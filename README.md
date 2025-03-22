# 🃏 Uno Card Game 🎮

This repository contains a Java implementation of the popular card game **Uno**. The project is designed to demonstrate object-oriented programming principles, game logic, and user interaction through a graphical user interface (GUI). 

🌟 **Features**:
- Supports 2-4 players.
- Implements classic Uno rules with special cards.
- Interactive GUI for a seamless gaming experience.
- Built using Java Swing for a clean and intuitive interface.

---

## 🎯 Overview

Uno is a classic card game where players take turns matching a card in their hand with the current card shown on top of the deck, either by color or number. The goal is to be the first player to get rid of all your cards.

This implementation includes a **GUI** for a more interactive experience and supports 2-4 players. It follows standard Uno rules, including special cards like **Skip**, **Reverse**, **Draw Two**, **Wild**, and **Wild Draw Four**.

---

## ✨ Features
- **Card Matching**: Play cards by matching color or number.
- **Special Cards**:
  - 🚫 **Skip**: Skips the next player's turn.
  - 🔄 **Reverse**: Reverses the order of play.
  - 📥 **Draw Two**: Forces the next player to draw 2 cards and skip their turn.
  - 🌈 **Wild**: Lets the player choose the next color.
  - 🌈📥 **Wild Draw Four**: Lets the player choose the next color and forces the next player to draw 4 cards and skip their turn.
- **Interactive GUI**: Built using Java Swing for a smooth and user-friendly experience.
- **Turn-Based Gameplay**: Supports 2-4 players.

---

## 🎮 How to Play
1. Each player starts with **7 cards**.
2. Players take turns playing a card that matches the top card of the discard pile by either **color** or **number**.
3. Use **special cards** to gain an advantage or disrupt your opponents.
4. The first player to get rid of all their cards **wins the game**!

---

## 🚀 Getting Started

### 📋 Prerequisites
- **Java Development Kit (JDK) 8 or later**.
- An IDE (e.g., IntelliJ, Eclipse) or a terminal to run the program.

### 📥 Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/mjak1385/UnoCardGame.git
   ```
2. Navigate to the project directory:
   ```bash
   cd UnoCardGame
   ```

---

## 🕹️ Running the Game
1. Compile the Java files:
   ```bash
   javac *.java
   ```
2. Run the game:
   ```bash
   java Driver
   ```
3. Follow the on-screen instructions to play the game.

---

## 🗂️ Project Structure

Here’s an overview of the Java files in the repository:

### 🃏 `Card.java`
- Represents a single Uno card with attributes like color and number.

### 🃏 `Deck.java`
- Manages the deck of cards, including creation, shuffling, and dealing.

### 🎮 `Driver.java`
- The main class that launches the game and handles user interactions.

### 🎲 `Game.java`
- Manages the game logic, including player turns, move validation, and special card effects.

### 👤 `Player.java`
- Represents a player and manages their hand of cards.

### 🖥️ `Table.java`
- Handles the graphical representation of the game table and cards.

---

## 🤝 Contributing
Contributions are welcome! If you'd like to contribute, please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes.
4. Submit a pull request.

---

Enjoy playing Uno! 🎉 If you have any questions or suggestions, feel free to open an issue or reach out to the contributors. 😊

---
