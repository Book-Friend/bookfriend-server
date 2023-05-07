package com.book.domain.user;

import com.book.domain.alarm.Notice;
import com.book.domain.MyBook.MyBook;
import com.book.domain.follow.Follow;
import com.book.domain.recommend.Like;
import com.book.domain.recommend.Recommend;
import com.book.domain.user.dto.request.UserUpdateDto;
import com.book.domain.user.dto.response.ProfileResDto;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    @NotNull
    private String name;

    private String intro;

    private String image;

    private LocalDateTime createAt;

    //ADMIN, USER
    private String role;

    //oauth
    private String provider;

    private String providerId;

    private boolean hidden;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyBook> myBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recommend> recommends = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> Notices = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> follower = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @PrePersist
    public void createTime(){
        this.createAt = LocalDateTime.now();
    }

    public void updateProfile(UserUpdateDto updateDto){
        this.name = updateDto.getName();
        this.intro = updateDto.getIntro();
    }

    public void updateImage(String imageName){
        this.image = imageName;
    }

    public boolean findBook(Long bookId){
        for (MyBook book:this.myBooks) {
            if(book.getBook().getId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

    public void addMyBook(MyBook myBook){
        this.myBooks.add(myBook);
    }

    public void addFollower(Follow follow){
        this.follower.add(follow);
    }

    public void addFollowing(Follow follow){
        this.following.add(follow);
    }
    public void addRec(Recommend rec) {
        this.recommends.add(rec);
    }

    public void deleteFollower(Follow follow){
        this.follower.remove(follow);
    }

    public void deleteFollowing(Follow follow){
        this.following.remove(follow);
    }

//    public void addShelf(String name){
//        shelf.add(name);
//    }

//    public List<MyBook> getBookInShelf(String name){
//        List<MyBook> books = new ArrayList<>();
//        for(MyBook book : this.myBooks){
//            if(book.getShelf().equals(name)) {
//                books.add(book);
//            }
//        }
//        return books;
//    }

    @Builder
    public User(String username, String password, String name, String email, String intro, String role, String provider, String providerId, boolean hidden){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.intro = intro;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.hidden = hidden;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void deleteMyBook(MyBook myBook){
        this.myBooks.remove(myBook);
    }


    public ProfileResDto toProfile(){
        return ProfileResDto.builder()
                .intro(this.intro)
                .name(this.name)
                .build();
    }

    public void deleteRec(Recommend recommend) {
        recommends.remove(recommend);
    }
}
