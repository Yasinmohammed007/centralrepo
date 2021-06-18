def call(String name = 'User') {
	echo "Welcome, ${name}."
}

def call(String desc = 'jom') {
	echo "Welcome, ${desc}."
}
sh """
	 echo Welcome, ${desc}.
"""