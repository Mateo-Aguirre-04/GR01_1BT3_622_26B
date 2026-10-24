pipeline {
    agent any

    triggers {
        // Jenkins consulta el repositorio periódicamente cada 5 minutos
        pollSCM('H/5 * * * *')
    }

    tools {
        // Debe coincidir con la herramienta configurada en tu jenkins.yaml
        maven 'Maven-3.9.9'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        timeout(time: 20, unit: 'MINUTES')
    }

    stages {
        stage('Verificar Entorno') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Compilar y Ejecutar Pruebas TDD') {
            steps {
                // Ejecuta la limpieza y las pruebas (ControlConsultaTest, ControlRegistroTest, etc.)
                sh 'mvn -B -ntp clean test'
            }
        }

        stage('Empaquetar WAR') {
            steps {
                // Genera el archivo gestion-refugio.war omitiendo las pruebas que ya pasaron
                sh 'mvn -B -ntp package -DskipTests'
            }
        }
    }

    post {
        always {
            // Recopila los resultados de las pruebas unitarias generados por Surefire
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/TEST-*.xml'
        }
    }
}