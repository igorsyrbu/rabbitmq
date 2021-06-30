variable "ssh_key_name" {
  description = "SSH key name for accessing RabbitMQ nodes"
}

variable "name" {
  default = "cluster"
}

variable "min_size" {
  description = "Minimum number of RabbitMQ nodes"
  default     = 3
}

variable "desired_size" {
  description = "Desired number of RabbitMQ nodes"
  default     = 3
}

variable "max_size" {
  description = "Maximum number of RabbitMQ nodes"
  default     = 3
}

variable "instance_type" {
  default = "m5.large"
}

variable "instance_volume_type" {
  default = "standard"
}

variable "instance_volume_size" {
  default = "0"
}

variable "instance_volume_iops" {
  default = "0"
}

