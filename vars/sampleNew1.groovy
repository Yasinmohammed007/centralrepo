
def vm_ip = '10.2.3.4' 
def call(body){
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    echo 'Checking Variables'
    //def message = "${config.commitMessage}".toString()
    def branch = config.brname
    def namei = config.name
    def lis_task = config.list_task
    println lis_task
    println "hi inside"
    println branch
    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    echo 'Building..'
                    if(lis_task.contains('deploy')){
                        echo "Welcome, ${namei}."
                        chat_in()
                    }
                    else{
                        println "Skipping deploy...."
                        println namei
                    }
                    
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing..'
                    println branch
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying....'
                    echo "Welcome"
                }
            }
        }
    }
}

def chat_in(){
    println "Yes, Deploy is added...."
}

