package com.assignment.article.rest.response.dto;

public class TagMetricesResponseDTO {

    private String tag;

    private int occurence;

    public TagMetricesResponseDTO(String tag, int occurence) {
        super();
        this.tag = tag;
        this.occurence = occurence;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + occurence;
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
        TagMetricesResponseDTO other = (TagMetricesResponseDTO) obj;
        if (occurence != other.occurence)
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        return true;
    }

}
