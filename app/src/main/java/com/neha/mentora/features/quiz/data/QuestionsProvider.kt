package com.neha.mentora.features.quiz.data

import com.neha.mentora.features.quiz.models.QuizQuestion
import kotlin.random.Random

object QuestionsProvider {

    // Build all questions (adapted from your Question.kt)
    fun getAllQuestions(): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        var idCounter = 1

        fun q(text: String, opts: List<String>, correct: Int, category: String, rangeStart: Int, rangeEnd: Int) {
            list.add(QuizQuestion("q${idCounter++}", text, opts, correct, category, rangeStart, rangeEnd))
        }

        // Math (some samples; full list expanded from your content)
        q("What is 2 + 3?", listOf("4","5","6","3"), 1, "Math", 3, 8)
        q("What is 9 - 2?", listOf("6","5","7","8"), 2, "Math", 3, 8)
        q("What number comes after 6?", listOf("5","6","7","8"), 2, "Math", 3, 8)
        q("What is 4 + 4?", listOf("6","8","7","9"), 1, "Math", 3, 8)
        q("What is 3 x 2?", listOf("6","5","4","7"), 0, "Math", 3, 8)

        q("What is 15 / 3?", listOf("5","6","4","3"), 0, "Math", 9, 14)
        q("What is 7 x 6?", listOf("42","36","48","40"), 0, "Math", 9, 14)
        q("What is 25 - 10?", listOf("15","10","20","5"), 0, "Math", 9, 14)
        q("What is 12 + 11?", listOf("21","23","22","24"), 1, "Math", 9, 14)
        q("What is 8 x 4?", listOf("36","30","32","28"), 2, "Math", 9, 14)

        q("What is the square of 6?", listOf("36","30","42","24"), 0, "Math", 15, 20)
        q("What is the cube of 3?", listOf("6","9","27","18"), 2, "Math", 15, 20)
        q("What is √49?", listOf("6","8","7","9"), 2, "Math", 15, 20)
        q("What is 100 - 45?", listOf("55","60","65","50"), 0, "Math", 15, 20)
        q("What is 14 x 2?", listOf("26","28","30","24"), 1, "Math", 15, 20)

        q("What is 15% of 200?", listOf("25","30","35","40"), 3, "Math", 21, 26)
        q("What is 13 x 11?", listOf("143","123","133","153"), 0, "Math", 21, 26)
        q("Solve: 50 / 2 + 10", listOf("35","30","40","25"), 0, "Math", 21, 26)
        q("What is √64?", listOf("6","7","8","9"), 2, "Math", 21, 26)
        q("What is 90 - 37?", listOf("53","55","57","52"), 0, "Math", 21, 26)

        q("What is 125 / 5?", listOf("25","30","20","24"), 0, "Math", 27, 100)
        q("What is the square root of 121?", listOf("10","11","12","13"), 1, "Math", 27, 100)
        q("What is 12.5 x 2?", listOf("25","20","22","30"), 0, "Math", 27, 100)
        q("Solve: (10 + 5) x 2", listOf("30","25","20","35"), 0, "Math", 27, 100)
        q("What is 100 - 36?", listOf("64","60","66","68"), 0, "Math", 27, 100)

        // Science (sample subset)
        q("What do plants need to grow?", listOf("Sunlight","Plastic","Stone","Paper"), 0, "Science", 3, 8)
        q("Which one is a source of light?", listOf("Moon","Sun","Tree","Cloud"), 1, "Science", 3, 8)
        q("What is water made of?", listOf("H2O","CO2","O2","NaCl"), 0, "Science", 3, 8)
        q("Which part of body helps us see?", listOf("Eyes","Hands","Feet","Nose"), 0, "Science", 3, 8)
        q("Which gas do we breathe in?", listOf("Oxygen","Carbon","Nitrogen","Helium"), 0, "Science", 3, 8)

        q("What planet do we live on?", listOf("Mars","Venus","Earth","Jupiter"), 2, "Science", 9, 14)
        q("Which part of plant makes food?", listOf("Leaf","Root","Stem","Flower"), 0, "Science", 9, 14)
        q("What gas do plants take in?", listOf("Oxygen","Carbon Dioxide","Nitrogen","Helium"), 1, "Science", 9, 14)
        q("What’s the boiling point of water?", listOf("100°C","90°C","80°C","70°C"), 0, "Science", 9, 14)
        q("Which organ pumps blood?", listOf("Heart","Liver","Brain","Kidney"), 0, "Science", 9, 14)

        q("Which planet is known as Red Planet?", listOf("Mars","Jupiter","Saturn","Venus"), 0, "Science", 15, 20)
        q("What is DNA?", listOf("Genetic Code","Energy","Protein","Sugar"), 0, "Science", 15, 20)
        q("Which vitamin from sunlight?", listOf("A","C","D","E"), 2, "Science", 15, 20)
        q("Unit of electric current?", listOf("Ampere","Volt","Ohm","Watt"), 0, "Science", 15, 20)
        q("What is H2SO4?", listOf("Sulfuric Acid","Hydrochloric Acid","Nitric Acid","Ammonia"), 0, "Science", 15, 20)

        q("What’s the largest organ?", listOf("Heart","Skin","Liver","Brain"), 1, "Science", 21, 26)
        q("Process of water cycle?", listOf("Evaporation","Condensation","Precipitation","All of these"), 3, "Science", 21, 26)
        q("Symbol for Sodium?", listOf("Na","So","Sn","Sm"), 0, "Science", 21, 26)
        q("What is force measured in?", listOf("Newton","Joule","Watt","Ohm"), 0, "Science", 21, 26)
        q("Gas in fire extinguishers?", listOf("CO2","O2","H2","N2"), 0, "Science", 21, 26)

        q("Chemical symbol for gold?", listOf("Au","Ag","Gd","Go"), 0, "Science", 27, 100)
        q("Study of cells?", listOf("Biology","Cytology","Botany","Zoology"), 1, "Science", 27, 100)
        q("Who developed gravity theory?", listOf("Einstein","Newton","Galileo","Tesla"), 1, "Science", 27, 100)
        q("What is ATP?", listOf("Energy","Acid","Protein","DNA"), 0, "Science", 27, 100)
        q("What causes tides?", listOf("Sun","Moon","Waves","Earthquake"), 1, "Science", 27, 100)

        // Sports (subset)
        q("How many players in a football team?", listOf("11","10","9","12"), 0, "Sports", 3, 8)
        q("What is used to hit the ball in cricket?", listOf("Bat","Stick","Club","Hand"), 0, "Sports", 3, 8)
        q("What sport uses a racket?", listOf("Tennis","Football","Hockey","Golf"), 0, "Sports", 3, 8)
        q("Which game uses a goal post?", listOf("Football","Tennis","Cricket","Chess"), 0, "Sports", 3, 8)
        q("What is played on a table with paddles?", listOf("Table Tennis","Badminton","Hockey","Snooker"), 0, "Sports", 3, 8)

        q("How long is a football match?", listOf("90 mins","60 mins","45 mins","120 mins"), 0, "Sports", 9, 14)
        q("Who is famous cricketer of Pakistan?", listOf("Babar Azam","Neymar","Messi","Ronaldo"), 0, "Sports", 9, 14)
        q("How many rings in Olympics logo?", listOf("5","6","4","3"), 0, "Sports", 9, 14)
        q("What is bowled in cricket?", listOf("Ball","Bat","Stick","Stone"), 0, "Sports", 9, 14)
        q("What sport has wickets?", listOf("Cricket","Football","Tennis","Hockey"), 0, "Sports", 9, 14)

        q("Who won FIFA 2022?", listOf("Argentina","France","Brazil","Germany"), 0, "Sports", 15, 20)
        q("Fastest man on Earth?", listOf("Usain Bolt","Messi","Ronaldo","Yohan Blake"), 0, "Sports", 15, 20)
        q("How many players in basketball?", listOf("5","6","7","8"), 0, "Sports", 15, 20)
        q("Most centuries in cricket?", listOf("Sachin","Babar","Virat","Afridi"), 0, "Sports", 15, 20)
        q("Tennis Grand Slam held in UK?", listOf("Wimbledon","US Open","French Open","Aussie Open"), 0, "Sports", 15, 20)

        // History (subset)
        q("Who discovered America?", listOf("Columbus","Newton","Einstein","Galileo"), 0, "History", 3, 8)
        q("What did cavemen use to write?", listOf("Stones","Pens","Chalk","Charcoal"), 3, "History", 3, 8)
        q("Who built pyramids?", listOf("Egyptians","Romans","Greeks","Indians"), 0, "History", 3, 8)
        q("Which animal was used in wars?", listOf("Horse","Tiger","Elephant","Monkey"), 2, "History", 3, 8)
        q("Which war ended in 1945?", listOf("WW2","WW1","Cold War","Civil War"), 0, "History", 3, 8)

        q("Who was first US president?", listOf("Lincoln","Washington","Obama","Adams"), 1, "History", 9, 14)
        q("Where is Great Wall?", listOf("China","India","Japan","Egypt"), 0, "History", 9, 14)
        q("Who ruled India before 1947?", listOf("British","French","Dutch","Chinese"), 0, "History", 9, 14)
        q("What was used before money?", listOf("Barter","Coins","Gold","Paper"), 0, "History", 9, 14)
        q("What year did WW1 start?", listOf("1914","1920","1939","1945"), 0, "History", 9, 14)

        // Tech, General Knowledge, Gaming etc.
        q("What is CPU?", listOf("Central Processing Unit","Central Power Unit","Control Program Unit","Computer Program Unit"), 0, "Tech", 3, 8)
        q("Which device is used to type?", listOf("Keyboard","Mouse","Monitor","Speaker"), 0, "Tech", 3, 8)
        q("What is AI?", listOf("Artificial Intelligence","Animal Intelligence","Active Internet","Augmented Info"), 0, "Tech", 15, 100)
        q("Programming language for Android?", listOf("Kotlin","Python","HTML","C#"), 0, "Tech", 15, 100)

        q("Color of sky?", listOf("Blue","Red","Green","Yellow"), 0, "General Knowledge", 3, 8)
        q("How many days in a week?", listOf("7","5","6","8"), 0, "General Knowledge", 3, 8)
        q("Largest animal?", listOf("Blue Whale","Elephant","Giraffe","Tiger"), 0, "General Knowledge", 3, 8)
        q("Capital of Pakistan?", listOf("Islamabad","Karachi","Lahore","Peshawar"), 0, "General Knowledge", 9, 14)

        q("Which game has creepers?", listOf("Minecraft","Roblox","PUBG","Valorant"), 0, "Gaming", 3, 100)
        q("Mario jumps to catch?", listOf("Coins","Stars","Balls","Bombs"), 0, "Gaming", 3, 100)
        q("What is PUBG?", listOf("Shooting game","Puzzle","Farming","Racing"), 0, "Gaming", 3, 100)

        // If you want more, expand here matching your repo's complete lists.

        return list.shuffled()
    }

    // Utility: pick questions by category and approximate age group (age integer)
    fun pickFor(category: String, age: Int, count: Int = 5): List<QuizQuestion> {
        val all = getAllQuestions().filter { it.category.equals(category, true) && age in it.ageRangeStart..it.ageRangeEnd }
        return if (all.size <= count) all.shuffled() else all.shuffled().take(count)
    }

    // helper to list available categories (based on provider)
    fun categories(): List<String> {
        return listOf("Math","Science","Sports","History","Tech","General Knowledge","Gaming")
    }
}
