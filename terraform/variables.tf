variable "do_token" {
  type        = string
  description = "DigitalOcean access token"
}

variable "domain" {
  type        = string
  default     = "romanow-alex.ru"
  description = "Base Domain name"
}

variable "certificate_name" {
  type        = string
  default     = "romanow-alex-certificate"
  description = "Certificate name"
}

variable "loadbalancer_name" {
  type        = string
  default     = "loadbalancer"
  description = "Load Balancer name"
}

variable "cluster_tags" {
  type        = list(string)
  default     = ["ui-test"]
  description = "Cluster tags"
}

variable "cluster_node_count" {
  type        = number
  default     = 3
  description = "Cluster node count"
}

variable "cluster_name" {
  type        = string
  default     = "ui-test-cluster"
  description = "Cluster name"
}

variable "cluster_region" {
  type        = string
  default     = "ams3"
  description = "Cluster region"
}

variable "cluster_size" {
  type        = string
  default     = "s-2vcpu-4gb"
  description = "Node size"
}

variable "hostnames" {
  type        = list(string)
  default     = ["moon", "todo-list"]
  description = "Hostnames"
}
