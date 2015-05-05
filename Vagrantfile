# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.require_version ">= 1.6.0"

ENV['VAGRANT_DEFAULT_PROVIDER'] = 'docker'

Vagrant.configure("2") do |config|

  config.vm.define "gainmaster-user-service" do |v|
    v.vm.provider "docker" do |d|
      # Docker image properties
      d.build_dir       = "."
      d.remains_running = true

      # Docker run configuration
      #d.cmd     = [""]
      d.volumes = ["/home/core/shared/docker-rabbitmq:/opt/shared:rw"]
      d.ports   = ["8081:80"]

      # Vagrant host configuration
      d.force_host_vm       = true
      d.vagrant_vagrantfile = "../coreos-vagrant/Vagrantfile"
      d.vagrant_machine     = "coreos-01"
    end
  end

  config.vm.define "gainmaster-oauth-service" do |v|
    v.vm.provider "docker" do |d|
      # Docker image properties
      d.image           = "gainmaster/gainmaster-oauth-service"
      d.remains_running = true

      # Docker run configuration
      d.ports   = ["8081:80"]

      # Vagrant host configuration
      d.force_host_vm       = true
      d.vagrant_vagrantfile = "../coreos-vagrant/Vagrantfile"
      d.vagrant_machine     = "coreos-01"
    end
  end

end
