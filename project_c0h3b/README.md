# Project -- No More

## Description

This is a simple role-playing game where player will control our main character -- Mr Qin to defeat the enemies and save  his beloved polar, Sola, who was kidnapped by an evil force. 

Anyone who is interested in this game can download and play and made a commit to this game.

Most of the features of this game was inspired by Cup Head and the character was built on my favorite streamer, Mr Quin.

## User Stories

- As a user, I want to add my player to the player list.

- As a user, I want to control the position of my character.

- As a user, I want enemies move according to player's position

- As a user, I want let the player attack enemies.

- As a user, I want my attack to do some damage to enemies and vice versa.

- As a user, I want to fire bullets at disired direction

- As a user, I want to save my process

- As a user,  I want to load my saved proess

- As a user, I want to initial my game

- As a user, I want to load my last game

- As a user, I wish to exit this game

## Acknowledge

The game render feature was inspired by https://www.youtube.com/watch?v=1gir2R7G9ws&t=103s

The game loop structure was inspired by https://www.youtube.com/watch?v=bWHYjLJZswQ&t=904s 


##Phase 4: Task 2
I chose the second option, that is "Include a type hierarchy in your code other than the one that uses the Saveable interface introduced in Phase 2.  You must have more than one subclass and your subclasses must have distinct functionality.  They must therefore override at least one method inherited from a super type and override it in different ways in each of the subclasses." to implement.

In my model, I have a GameCharacter abstract class, which has fields: x, y of the character, its velocity along x and y axis, its height and width which are all double value. It also contains the health which is integer and a boolean indicating whether or not this object is defeated. 

&nbsp;&nbsp;&nbsp;&nbsp; The abstract class has a constructor which takes two double as input and initialize the position, health, defeated of the character. It also has an inBoundary method determine whether or not the character is in game panel, an abstract **tick** method . This method renders the next state of the object and it needs its subclass to implement, 16 setter and getter methods for the above 8 fields

There is also an interface called CanFireBullets that has a fireBullets method decleared inside.

BasicEnemy class extends Gamecharacter and implements saveable and CanFireBullets, it represents a basicenemy, besides the above-mentioned 8 fields, it also has a Player object which directs to the player, a bullets arrylist which represents the bullets the enemy has. It also have one double constant SPEED represents the speed of the enemy and one BULLETDAMAGE represents how much damage its bullets could make to the player.

&nbsp;&nbsp;&nbsp;&nbsp; The BasicEnemy class has a constructor, and implements tick , fireBullets which will fire bullets towards player and save method. They were decleared in GameCharacter, CanFireBullets and saveable respectively.

Bullet class extends GameCharacter and implements Saveable, it has SPEED, HEIGHT and WIDTH constant, an additional damage and direction field. It implements tick and save method.

Melee class extends GameCharacter, it has HEIGHT and WIDTH constant, an additional damage and direction field and a boolean attack indicating whether or not it is attacking. It implements tick method.

Player extends Gamecharacter and implements Saveable and CanFireBullets, it implements thick, fireBullets and save method of its superclass.

##Phase 4: Task 3
The GameHandler class have an ArrayList of bullets, and has method to handle the characters and bullets (add or remove) which violates SRP and causes poor cohesion, so we need to add an independent class called Handlebullets to solve this problem.

In Game class, it needs to handle a bunch of enemies which are not supposed to be performed by Game, this will be thrown to GameHandler to handle.

