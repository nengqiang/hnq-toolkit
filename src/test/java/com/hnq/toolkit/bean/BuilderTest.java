package com.hnq.toolkit.bean;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author henengqiang
 * @date 2021/01/26
 */
class BuilderTest {

    @Test
    void testBuild() {
        GirlFriend girlFriend = Builder.of(GirlFriend::new)
                .with(GirlFriend::setName, "Alice")
                .with(GirlFriend::setAge, 20)
                .with(GirlFriend::setVitalStatistics, 33, 23, 33)
                .with(GirlFriend::setBirthday, "2000-08-11")
                .with(GirlFriend::setAddress, "Chengdu")
                .with(GirlFriend::setMobile, "11111111111")
                .with(GirlFriend::setEmail, "alice678@email.com")
                .with(GirlFriend::setHairColor, "black")
                .with(GirlFriend::addHobby, "Shopping")
                .with(GirlFriend::addHobby, "GoShopping")
                .with(GirlFriend::addHobby, "Watch TV")
                .with(GirlFriend::addGift, "valentineSDayPresent", "queenSEra")
                .with(GirlFriend::addGift, "birthdayPresent", "audiFlameBlueGold")
                .with(GirlFriend::addGift, "anniversaryGift", "alimaRedTubeLipstick")
                .build();

        System.out.println(girlFriend);
    }

    private static class GirlFriend {
        private String name;
        private int age;
        private int bust;
        private int waist;
        private int hips;
        private List<String> hobby;
        private String birthday;
        private String address;
        private String mobile;
        private String email;
        private String hairColor;
        private Map<String, String> gift;

        public void addHobby(String hobby) {
            this.hobby = Optional.ofNullable(this.hobby).orElse(new ArrayList<>());
            this.hobby.add(hobby);
        }
        public void addGift(String day, String gift) {
            this.gift = Optional.ofNullable(this.gift).orElse(new HashMap<>());
            this.gift.put(day, gift);
        }
        public void setVitalStatistics(int bust, int waist, int hips) {
            this.bust = bust;
            this.waist = waist;
            this.hips = hips;
        }

        @Override
        public String toString() {
            return "GirlFriend{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", bust=" + bust +
                    ", waist=" + waist +
                    ", hips=" + hips +
                    ", hobby=" + hobby +
                    ", birthday='" + birthday + '\'' +
                    ", address='" + address + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", hairColor='" + hairColor + '\'' +
                    ", gift=" + gift +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getBust() {
            return bust;
        }

        public void setBust(int bust) {
            this.bust = bust;
        }

        public int getWaist() {
            return waist;
        }

        public void setWaist(int waist) {
            this.waist = waist;
        }

        public int getHips() {
            return hips;
        }

        public void setHips(int hips) {
            this.hips = hips;
        }

        public List<String> getHobby() {
            return hobby;
        }

        public void setHobby(List<String> hobby) {
            this.hobby = hobby;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHairColor() {
            return hairColor;
        }

        public void setHairColor(String hairColor) {
            this.hairColor = hairColor;
        }

        public Map<String, String> getGift() {
            return gift;
        }

        public void setGift(Map<String, String> gift) {
            this.gift = gift;
        }
    }

}