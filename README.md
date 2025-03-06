
> This will be updated often
# UML Iteration 1 Chill Team 7 Diagram
![UMLChillTeam7 (1)](https://github.com/user-attachments/assets/aa7a0af4-79ad-4de0-8e94-296c68bec234)
 <br> Here is our UML diagram based on our requirements from our phase 1 planning and finalizations.


# Coding Practices
## Comments
1) Descriptive comments for functions, classes, etc. (if it isn't intuitive)
```java
// Adds two numbers
public void addNumsFunction(int num1, int num2){}
```
2) Comments for code developed by others. Please prepend your name
```java
// Aidan: What is this for?
System.out.println(var1);
```
## Classes and Objects
If something can be a class, and should be a class, implement it as a class!

## Master Branch V.S. Other Branches
- If you want to implement simple fixes, or add commments, commit and push to main
- If you are working on a large feature, such as a user story, push to another branch

# Simplified Project Structure
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
- Commit often!
