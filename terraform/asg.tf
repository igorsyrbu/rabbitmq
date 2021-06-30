resource "aws_autoscaling_group" "rabbitmq" {
  name                      = local.cluster_name
  min_size                  = var.min_size
  desired_capacity          = var.desired_size
  max_size                  = var.max_size
  health_check_grace_period = 300
  health_check_type         = "ELB"
  force_delete              = true
  launch_configuration      = aws_launch_configuration.rabbitmq.name
  load_balancers            = [aws_elb.elb.name]
  vpc_zone_identifier       = module.vpc.public_subnets

  tag {
    key                 = "Name"
    value               = local.cluster_name
    propagate_at_launch = true
  }
}

resource "aws_launch_configuration" "rabbitmq" {
  name                 = local.cluster_name
  image_id             = data.aws_ami_ids.ami.ids[0]
  instance_type        = var.instance_type
  key_name             = var.ssh_key_name
  security_groups      = [aws_security_group.rabbitmq_nodes.id]
  iam_instance_profile = aws_iam_instance_profile.profile.id
  user_data            = data.template_file.cloud-init.rendered

  root_block_device {
    volume_type           = var.instance_volume_type
    volume_size           = var.instance_volume_size
    iops                  = var.instance_volume_iops
    delete_on_termination = true
  }

  lifecycle {
    create_before_destroy = true
  }
}

data "template_file" "cloud-init" {
  template = file("${path.module}/cloud-init.yaml")

  vars = {
    sync_node_count = 3
    asg_name        = local.cluster_name
    region          = data.aws_region.current.name
    admin_password  = random_string.admin_password.result
    rabbit_password = random_string.rabbit_password.result
    secret_cookie   = random_string.secret_cookie.result
    message_timeout = 3 * 24 * 60 * 60 * 1000 # 3 days
  }
}

data "aws_ami_ids" "ami" {
  owners = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn-ami-hvm-2017*-gp2"]
  }
}
