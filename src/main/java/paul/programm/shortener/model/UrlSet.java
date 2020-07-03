package paul.programm.shortener.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@JsonAutoDetect
@Entity
@ApiModel(description = "Set of URL's")
public class UrlSet {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @ApiModelProperty(notes = "The unique ID in database")
    private long id;
    @ApiModelProperty(notes = "Original URL")
    private String original;
    @ApiModelProperty(notes = "Short URL")
    private String shortened;

    @CreationTimestamp
    @ApiModelProperty(notes = "Creation timestamp")
    private Timestamp timestamp;

    public UrlSet() { }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getShortened() {
        return shortened;
    }

    public void setShortened(String shortened) {
        this.shortened = shortened;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlSet urlSet = (UrlSet) o;
        return id == urlSet.id &&
                Objects.equals(original, urlSet.original) &&
                Objects.equals(shortened, urlSet.shortened) &&
                Objects.equals(timestamp, urlSet.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, original, shortened, timestamp);
    }
}
