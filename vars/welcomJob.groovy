
def call(body){
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    def git_cred = "${env.git_credential_id}"
    def job_name = env.JOB_NAME.split('/')[-1]
    def branch = config.brname
    def namei = config.name
    def chartName = config.chart_name
    def chartLocation = config.chart_location
    def lis_task = config.list_task
    println namei
    println lis_task
    println "hi inside"
    println branch
    def build_id = "${env.BUILD_ID}"
    def date = new Date()
    def unique_dir
    def docker_image_path
    node{
        def current_dir = pwd()
        unique_dir = "${current_dir}"+"/"+"${build_id}"
        echo "git_cred are: ${git_cred}"
        dir(unique_dir){
            try{
                checkout scm
                println """
                    Machine Name
                    ls -lrth
                """
                checkout_helm_chart_reference()
                sh"""
                    pwd
                    
                """
                stage("box_install"){
                    sh """ ls -lrth ${unique_dir};pwd"""

                }

                stage('Setup Base k3s Environment'){
                    // checkout_box_details_repo(branch, git_cred)
                    kube_config_fullpath = unique_dir+'/vars/config'
                        
                    sh """
                        pwd
                        kubectl get pods -A --kubeconfig ${kube_config_fullpath}

                    """
                }

                // stage('Bazel Build'){
                //     sh """
                //         bazel build //:paloma-config-service

                //         pwd
                //     """
                // }
                // stage('Bazel Test'){
                //     sh """
                //         bazel test //:paloma-config-service_test

                //         pwd
                //     """
                // }

                stage('Build Docker Images'){
                    // checkout_box_details_repo(branch, git_cred)
                    kube_config_fullpath = unique_dir+'/vars/config'
                    docker_image_path = unique_dir+'/tests/'
                        
                    sh """
                        pwd
                        cd ${docker_image_path};docker build --tag python-docker .
                        

                    """
                }

                stage('Deploy Chart'){
                    // checkout_box_details_repo(branch, git_cred)
                    kube_config_fullpath = unique_dir+'/vars/config'
                        
                    sh """
                        pwd
                        cd ${chartLocation}/${chartName};helm install testchart . --kubeconfig ${kube_config_fullpath}

                    """
                }

                stage('Tests/Component/IntegTests Chart'){
                    // checkout_box_details_repo(branch, git_cred)
                    kube_config_fullpath = unique_dir+'/vars/config'
                        
                    sh """
                        sleep 10
                        pwd
                        helm test testchart --logs --kubeconfig ${kube_config_fullpath}

                    """
                }

            }
            catch (err) {
                echo 'Something went wrong:' + err
                throw err
            }
            finally{
                echo "Im in final...."
            }
        }
    }
}

def checkout_helm_chart_reference() {
    // git branch: "master", changelog: false, credentialsId: 'c2ee76ab-2227-4b93-b008-084312e64921', poll: false, url: 'git@gitlab-gxp.cloud.health.ge.com:edison-imaging-service-poc/catalyst/helm_chart_reference.git'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Yasinmohammed007/Firstone.git']]])
}