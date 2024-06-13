terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = ">= 2.23"
    }
    helm = {
      source  = "hashicorp/helm"
      version = ">= 2.0.1"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = ">= 2.12"
    }
  }
  required_version = ">= 1.3"
}

terraform {
  cloud {
    organization = "Romanow-Pride"
    workspaces {
      name = "todo-list"
    }
  }
}

provider "digitalocean" {
  token = var.do_token
}
