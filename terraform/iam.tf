resource "aws_iam_role" "role" {
  name               = local.cluster_name
  assume_role_policy = data.aws_iam_policy_document.policy_doc.json
}

resource "aws_iam_role_policy" "policy" {
  name = local.cluster_name
  role = aws_iam_role.role.id

  policy = jsonencode({
    Version: "2012-10-17",
    Statement: [
      {
        Effect: "Allow",
        Action: [
          "autoscaling:DescribeAutoScalingInstances",
          "ec2:DescribeInstances"
        ],
        Resource: [
          "*"
        ]
      }
    ]
  })

}

resource "aws_iam_instance_profile" "profile" {
  name_prefix = local.cluster_name
  role        = aws_iam_role.role.name
}

data "aws_iam_policy_document" "policy_doc" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ec2.amazonaws.com"]
    }
  }
}