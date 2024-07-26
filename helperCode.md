# Ensure jenv is properly set up
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
echo 'eval "$(jenv init -)"' >> ~/.zshrc
source ~/.zshrc

# Rehash jenv
jenv rehash

# Add all Java versions
jenv add /Library/Java/JavaVirtualMachines/jdk-22.jdk/Contents/Home
jenv add /Users/arunbasil/Library/Java/JavaVirtualMachines/temurin-20.0.2/Contents/Home
jenv add /Users/arunbasil/Library/Java/JavaVirtualMachines/jbr-17.0.11/Contents/Home
jenv add /Library/Java/JavaVirtualMachines/openjdk-21.0.3.jdk/Contents/Home

# Set the global version
jenv global 22.0.2

# Verify the active version
jenv version
java -version

cd /path/to/project
jenv local 20.0.2
java -version  # Should show Java 20.0.2

jenv shell 17.0.11
java -version  
