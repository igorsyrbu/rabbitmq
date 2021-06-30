output "rabbitmq_elb_dns" {
  value = aws_elb.elb.dns_name
}

output "admin_password" {
  value     = random_string.admin_password.result
}

output "rabbit_password" {
  value     = random_string.rabbit_password.result
}

output "secret_cookie" {
  value     = random_string.secret_cookie.result
}

