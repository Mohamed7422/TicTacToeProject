package tictactoeclientapplication;


    public class Player {
        private String name;
        private int score;
        private String status; // Online, In Game, Offline

        public Player(String name, int score, String status) {
            this.name = name;
            this.score = score;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
