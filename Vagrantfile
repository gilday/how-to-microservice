# -*- mode: ruby -*-
# vi: set ft=ruby :

# how-to-microservice CentOS 7 Vagrant box
#
# this box is a base CentOS 7 image for testing the RPM install

Vagrant.configure(2) do |config|
  # use geerlingguy/centos7 instead of centos/7 because the official centos 7
  # box does not work with virtualbox file shares
  config.vm.box = "centos/7"

  # forward the how-to-microservice port
  config.vm.network "forwarded_port", guest: 8000, host: 8001

  # create a private network, which allows host-only access to the machine
  config.vm.network "private_network", ip: "192.168.33.10"

  # share the project directory with the vm
  config.vm.synced_folder ".", "/vagrant"

  # only need 512MB memory
  config.vm.provider "virtualbox" do |vb|
    vb.memory = "512"
  end

  # provision should remove the current rpm and install a new one
  config.vm.provision "shell", inline: <<-SHELL
    sync_dir=/vagrant
    rpm=($sync_dir/build/distributions/*.rpm)
    if [ ! -f $rpm ]; then
      echo "how-to-microservice RPM not found"
      exit 1
    fi
    sudo yum erase -y how-to-microservice
    sudo yum install -y $rpm
    sudo systemctl restart how-to-microservice
  SHELL
end

