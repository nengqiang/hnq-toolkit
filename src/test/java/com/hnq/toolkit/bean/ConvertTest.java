package com.hnq.toolkit.bean;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author henengqiang
 * @date 2021/03/26
 */
public class ConvertTest {

    @Test
    void testConvert() {
        GirlFriend girlOne = Builder.of(GirlFriend::new).build();
        System.out.println(girlOne);

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
        girlOne = BeanUtils.convert(girlFriend, GirlFriend.class);
        System.out.println(girlOne);
    }

    @Test
    void testConvertList() {
        Apple a1 = Builder.of(Apple::new)
                .with(Apple::setName, "红富士")
                .with(Apple::setWeight, 1.1D)
                .build();
        Apple a2 = Builder.of(Apple::new)
                .with(Apple::setName, "酸酸青苹果")
                .with(Apple::setWeight, 0.8D)
                .build();
        System.out.println(a1);
        System.out.println(a2);
        List<Apple> apples = Lists.newArrayList(a1, a2);
        List<Apple> copyApples = BeanUtils.convert(apples, Apple.class);
        copyApples.forEach(a -> a.setWeight(0.9D));
        copyApples.forEach(System.out::println);
    }

}
