provider "aws" {
  region = "us-east-1"
}

data "aws_region" "current" {
}

locals {
  cluster_name = "rabbitmq-${var.name}"
}

resource "random_string" "admin_password" {
  length  = 32
  special = false
}

resource "random_string" "rabbit_password" {
  length  = 32
  special = false
}

resource "random_string" "secret_cookie" {
  length  = 64
  special = false
}