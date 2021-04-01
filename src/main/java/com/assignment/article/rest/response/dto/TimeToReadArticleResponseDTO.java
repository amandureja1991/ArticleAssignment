package com.assignment.article.rest.response.dto;

public class TimeToReadArticleResponseDTO {

    private String articleId;

    private TimeToRead timeToRead;

    private class TimeToRead {

        int mins;

        int seconds;

        public TimeToRead() {
            super();
        }

        public TimeToRead(int mins, int seconds) {
            super();
            this.mins = mins;
            this.seconds = seconds;
        }

        public int getMins() {
            return mins;
        }

        public void setMins(int mins) {
            this.mins = mins;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + mins;
            result = prime * result + seconds;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TimeToRead other = (TimeToRead) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            if (mins != other.mins)
                return false;
            if (seconds != other.seconds)
                return false;
            return true;
        }

        private TimeToReadArticleResponseDTO getEnclosingInstance() {
            return TimeToReadArticleResponseDTO.this;
        }

    }

    public TimeToReadArticleResponseDTO() {
        super();
    }

    public TimeToReadArticleResponseDTO(String articleId, int mins, int seconds) {
        super();
        this.articleId = articleId;
        this.timeToRead = new TimeToRead(mins, seconds);
    }

    public String getArticleId() {
        return articleId;
    }

    public TimeToRead getTimeToRead() {
        return timeToRead;
    }

    public void setMins(int mins) {
        this.getTimeToRead().setMins(mins);
    }

    public void setSeconds(int seconds) {
        this.getTimeToRead().setSeconds(seconds);
    }

    public int getSeconds() {
        return this.getTimeToRead().getSeconds();
    }

    public int getMins() {
        return this.getTimeToRead().getMins();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
        result = prime * result + ((timeToRead == null) ? 0 : timeToRead.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimeToReadArticleResponseDTO other = (TimeToReadArticleResponseDTO) obj;
        if (articleId == null) {
            if (other.articleId != null)
                return false;
        } else if (!articleId.equals(other.articleId))
            return false;
        if (timeToRead == null) {
            if (other.timeToRead != null)
                return false;
        } else if (!timeToRead.equals(other.timeToRead))
            return false;
        return true;
    }

}
