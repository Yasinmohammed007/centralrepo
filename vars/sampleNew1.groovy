
def vm_ip = '10.2.3.4' 
def call(body){
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    echo 'Checking Variables'
    //def message = "${config.commitMessage}".toString()
    def branch = config.brname
    println "hi inside"
    println branch

}
def call(String name = 'User') {
		echo "Welcome, ${name}."
        pipeline {
            agent any

            stages {
                stage('Build') {
                    steps {
                        echo 'Building..'
                        echo "Welcome, ${name}."
                    }
                }
                stage('Test') {
                    steps {
                        echo 'Testing..'
                        echo "Welcome, ${vm_ip}."
                    }
                }
                stage('Deploy') {
                    steps {
                        echo 'Deploying....'
                    }
                }
            }
        }
}

