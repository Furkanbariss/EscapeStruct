# Escape Struct Simulator

A turn-based maze simulation game developed for the CENG 202 - Data Structures course. The game challenges agents to escape from a dynamic maze using manually implemented data structures such as stacks, queues, and circular corridors.

---

## 🚀 Project Description

This simulation allows 1 or 2 agents to navigate through a maze filled with traps, power-ups, walls, and a moving corridor system. The goal is to reach the `G` (goal) tile before the maximum number of turns is reached. The maze is randomly generated each run and guaranteed to be solvable.

All core data structures (stack, queue, circular logic) are implemented manually without using Java's built-in classes.

---

## 🔧 How to Run

> 💡 Requires: Java 8 or higher and IntelliJ IDEA (recommended)

1. Clone or download the project.
2. Open the project in IntelliJ IDEA.
3. Run the `Main` class.
4. Enter the required simulation parameters when prompted.

---

## 📥 Input Format

The user is prompted for the following inputs:

- Maze Width (recommended: 8 or more)
- Maze Height
- Agent Count (1 or 2)
- Maximum Turns
- Trap Frequency (%)
- Power-up Frequency (%)
- Wall Frequency (%)
- Log File Name (e.g., `log.txt`)

---

## 📤 Output

- Real-time ASCII maze display
- Agent movement and tile interaction logs
- Summary of simulation at the end
- All logs are written to the file specified by the user

---

## 🧠 Data Structures Used

| Structure      | Class Name        | Purpose                            |
|----------------|-------------------|------------------------------------|
| Stack (LIFO)   | `AgentStack`      | Stores agent move history          |
| Queue (FIFO)   | `CustomQueue`     | Manages agent turns and BFS        |
| Circular List  | `CircularCorridor`| Rotates maze rows every 5 rounds   |
| 2D Grid        | `MazeTile[][]`    | Represents the maze                |

---

## 👨‍💻 Developer

- **Furkan Barış Sönmezışık** 

---

## 📄 License

This project is licensed under the [MIT License](./LICENSE).
