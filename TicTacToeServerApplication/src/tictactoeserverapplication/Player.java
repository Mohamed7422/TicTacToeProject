package tictactoeserverapplication;


    public class Player {
        private String name;
        private int score;
        private String status;
        private String password;// Online, In Game, Offline
        
        public Player(){};

        public Player(String name,String password, int score, String status) {
            this.name = name;
            this.score = score;
            this.status = status;
             this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
         public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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
