package com.hnq.toolkit.bean;

import org.junit.jupiter.api.Test;

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
                .with(GirlFriend::setMobile, "19945644563")
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

}