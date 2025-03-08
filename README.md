# Running the Program
1) Navigate to the directory: `chillteam7-game-catalog`
2) Open up command prompt and ensure java and maven are installed by running: `java & maven`
4) Run the program: `mvn compile exec:java -Dexec.mainClass="Main"`
---
# CatalogueUI Iteration 2 Chill Team 7 StateChart Diagram
insert img soon
 <br> Here is our StateChart diagram for catalogueUI as of iteration 1.

# UML Iteration 1 Chill Team 7 Diagram
![UMLChillTeam7 (1)](https://github.com/user-attachments/assets/aa7a0af4-79ad-4de0-8e94-296c68bec234)
 <br> Here is our UML diagram based on our requirements from our phase 1 planning and finalizations.

# UML Sequence Diagram Chill Team 7 Diagram
![ChillTeam7umlSequence](https://github.com/user-attachments/assets/725c0f66-b299-4c4e-9701-d28a525aaaf7)
  <br> Here is our UML sequence diagram for adding a game.
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
