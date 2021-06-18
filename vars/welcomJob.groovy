
def call(String name = 'User') {
	echo "Welcome, ${name}."
}
sh """
	 echo Welcome, ${desc}.
"""