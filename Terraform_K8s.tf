terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
      version = "4.51.0"
    }
  }
}

provider "google" {
  credentials = file("credentials.json")
  project     = "csci5409-kubernetes-417610"
  region      = "us-central1"
}

resource "google_container_cluster" "primary" {
  name     = "dockerapp-cluster"
  location = "us-central1"
  initial_node_count = 1

  node_config {
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
    machine_type = "e2-micro"
    disk_size_gb = 10
    disk_type = "pd-standard"
    image_type = "COS_CONTAINERD"
    preemptible = false
    spot            = false
    service_account = <service_account>
  }
}

