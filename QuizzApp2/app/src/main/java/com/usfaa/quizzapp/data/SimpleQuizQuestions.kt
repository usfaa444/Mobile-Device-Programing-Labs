package com.usfaa.quizzapp.data

import com.usfaa.quizzapp.data.models.QuestionData
import com.usfaa.quizzapp.data.models.Response

class SimpleQuizQuestions {
    companion object {
        val questions = mutableListOf<QuestionData>(
            QuestionData(1, "Android is -"),
            QuestionData(2, "Under which of the following Android is licensed?"),
            QuestionData(3, "For which of the following Android is mainly developed?"),
            QuestionData(4, "Which of the following is the first mobile phone released that ran the Android OS?"),
            QuestionData(5, "Which of the following virtual machine is used by the Android operating system?"),
            QuestionData(6, "Android is based on which of the following language?"),
            QuestionData(7, "APK stands for -"),
            QuestionData(8, "What does API stand for?"),
            QuestionData(9, "Which of the following converts Java byte code into Dalvik byte code?"),
            QuestionData(10, "How can we stop the services in android?"),
            QuestionData(11, "What is an activity in android?"),
            QuestionData(12, "How can we kill an activity in android?"),
            QuestionData(13, "ADB stands for -"),
            QuestionData(14, "On which of the following, developers can test the application, during developing the android applications?"),
            QuestionData(15, "Which of the following is the first callback method that is invoked by the system during an activity life-cycle?"),
        )

        val responses = mutableListOf<Response>(
            //Question 1 answers
            Response(1, "An Operation System", 1, true),
            Response(2, "A Web Browser", 1, false),
            Response(3, "A Web Server", 1, false),
            Response(4, "None of the above", 1, false),

            //Question 2 answers
            Response(5, "OSS", 2, false),
            Response(6, "Sourceforge", 2, false),
            Response(7, "Apache/MIT", 2, true),
            Response(8, "None of the above", 2, false),

            //Question 3 answers
            Response(9, "Servers", 3, false),
            Response(10, "Desktop", 3, false),
            Response(11, "Laptop", 3, false),
            Response(12, "Mobile Device", 3, true),

            //Question 4 answers
            Response(13, "HTC Hero", 4, false),
            Response(14, "Google gPhone", 4, false),
            Response(15, "T - Mobile G1", 4, true),
            Response(16, "None of the above", 4, false),

            //Question 5 answers
            Response(17, "JVM", 5, false),
            Response(18, "Dalvik Virtual Machine", 5, true),
            Response(19, "Simple Virtual Machine", 5, false),
            Response(20, "None of the above", 5, false),

            //Question 6 answers
            Response(21, "Java", 6, true),
            Response(22, "C++", 6, false),
            Response(23, "C", 6, false),
            Response(24, "None of the above", 6, false),

            //Question 7 answers
            Response(25, "Android Phone Kit", 7, false),
            Response(26, "Android Page Kit", 7, false),
            Response(27, "Android Package Kit", 7, true),
            Response(28, "None of the above", 7, false),

            //Question 8 answers
            Response(29, "Application Programming Interface", 8, true),
            Response(30, "Android Programming Interface", 8, false),
            Response(31, "Android Page Interface", 8, false),
            Response(32, "Application Page Interface", 8, false),

            //Question 9 answers
            Response(33, "Dalvik converter", 9, false),
            Response(34, "Dex compiler", 9, true),
            Response(35, "Mobile Interpretive Compiler (MIC)", 9, false),
            Response(36, "None of the above", 9, false),

            //Question 10 answers
            Response(37, "By using the stopSelf() and stopService() method", 10, true),
            Response(38, "By using the finish() method", 10, false),
            Response(39, "By using system.exit() method", 10, false),
            Response(40, "None of the above", 10, false),

            //Question 11 answers
            Response(41, "Android class", 11, false),
            Response(42, "Android package", 11, false),
            Response(43, "A single screen in an application with supporting java code", 11, true),
            Response(44, "None of the above", 11, false),

            //Question 12 answers
            Response(45, "Using finish() method", 12, false),
            Response(46, "Using finishActivity(int requestCode)", 12, false),
            Response(47, "Both (a) and (b)", 12, true),
            Response(48, "Neither (a) nor (b)", 12, false),

            //Question 13 answers
            Response(49, "Android debug bridge", 13, true),
            Response(50, "Android delete bridge", 13, false),
            Response(51, "Android destroy bridge", 13, false),
            Response(52, "None of the above", 13, false),

            //Question 14 answers
            Response(53, "Third-party emulators", 14, false),
            Response(54, "Emulator included in Android SDK", 14, false),
            Response(55, "Physical android phone", 14, false),
            Response(56, "All of the above", 14, true),

            //Question 15 answers
            Response(57, "MAC", 15, false),
            Response(58, "Windows", 15, false),
            Response(59, "Linux", 15, true),
            Response(60, "Redhat", 15, false)
        )
    }
}