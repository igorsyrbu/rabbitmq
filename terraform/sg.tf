resource "aws_security_group" "rabbitmq_elb" {
  name        = "rabbitmq_elb-${var.name}"
  description = "Security Group for the rabbitmq elb"
  vpc_id      = module.vpc.vpc_id

  ingress {
    protocol        = "tcp"
    from_port       = 80
    to_port         = 80
    cidr_blocks     = ["0.0.0.0/0"]
  }

  ingress {
    protocol        = "tcp"
    from_port       = 5672
    to_port         = 5672
    cidr_blocks     = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "rabbitmq ${var.name} elb"
  }
}

resource "aws_security_group" "rabbitmq_nodes" {
  name        = "${local.cluster_name}-nodes"
  vpc_id      = module.vpc.vpc_id
  description = "Security Group for the rabbitmq nodes"

  ingress {
    protocol        = "tcp"
    from_port       = 22
    to_port         = 22
    cidr_blocks     = ["0.0.0.0/0"]
  }

  ingress {
    protocol  = -1
    from_port = 0
    to_port   = 0
    self      = true
  }

  ingress {
    protocol        = "tcp"
    from_port       = 5672
    to_port         = 5672
    security_groups = [aws_security_group.rabbitmq_elb.id]
  }

  ingress {
    protocol        = "tcp"
    from_port       = 15672
    to_port         = 15672
    security_groups = [aws_security_group.rabbitmq_elb.id]
  }

  egress {
    protocol  = "-1"
    from_port = 0
    to_port   = 0

    cidr_blocks = [
      "0.0.0.0/0",
    ]
  }

  tags = {
    Name = "rabbitmq ${var.name} nodes"
  }
}