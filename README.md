# Coding Practices
## Comments
1) Descriptive comments for functions, classes, etc.
```java
// Adds Two numbers
public void addNumsFunction(int num1, int num2){}
```
2) Comments for code developed by others. Please prepend your name
```java
// Aidan: What is this for?
System.out.println(var1);
```
## Classes and Objects
If something can be a class, and should be a class, implement it as a class!

# Projcet Structure
```
game-catalog/
├── src/
│   ├── backend/
│   │   ├── Database.java
│   │   ├── other-files.java
│   ├── frontend/
│   │   ├── catalogUI.java
│   │   ├── other-files.java
│   ├── main.java
```
# Other
- Frontend leads and backend leads have the final say in their respective areas
- If you get merge conflicts, run `git mergetool`
