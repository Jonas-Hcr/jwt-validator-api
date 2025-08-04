resource "aws_instance" "jwt_api" {
  ami           = "ami-0c7217cdde317cfec" # Ubuntu 22.04 us-east-1
  instance_type = "t2.micro"
  key_name      = var.key_name
  security_groups = [aws_security_group.jwt_sg.name]

  tags = {
    Name = "jwt-api-instance"
  }

  user_data = file("startup.sh")
}

resource "aws_security_group" "jwt_sg" {
  name        = "jwt-api-sg"
  description = "Allow 8080 for Spring Boot"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
