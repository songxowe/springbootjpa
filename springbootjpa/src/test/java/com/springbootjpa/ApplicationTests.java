package com.springbootjpa;

import com.springbootjpa.domain.Movie;
import com.springbootjpa.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  @Autowired
  private MovieService movieService;

  @Test
  public void findByActionTimeBetween() {
    Date beginDate = new Date();
    Date endDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      beginDate = sdf.parse("2018-04-10 00:00:00");
      endDate = sdf.parse("2018-04-11 23:59:59");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    for (Movie movie : movieService.findByActionTimeBetween(beginDate, endDate)) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void findByNameNotLikeAAndPrice() {
    for (Movie movie : movieService.findByNameNotLikeAndPrice("%头%", 70d)) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void findByNameNotLike() {
    for (Movie movie : movieService.findByNameNotLike("%头%")) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void findByNameLike() {
    for (Movie movie : movieService.findByNameLike("%头%")) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void findByMovieName() {
    for (Movie movie : movieService.findByMovieName("头号玩家")) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void save() {
    Movie movie = new Movie();
    // 新增(无 id) | 修改(有 id)
    //movie.setId(2);
    movie.setName("变形金刚");
    movie.setPrice(80d);
    movie.setActionTime(new Date());
    movieService.save(movie);
  }

  @Test
  public void findAll() {
    for (Movie movie : movieService.findAll()) {
      System.out.println(movie.getName() + " " + movie.getPrice());
    }
  }

  @Test
  public void findById() {
    Optional<Movie> movie = movieService.findById(4);
    System.out.println(movie.get().getName() + " " + movie.get().getPrice());
  }

  @Test
  public void deleteById() {
    movieService.deleteById(3);
  }

}
