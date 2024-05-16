package com.chess.chessApp;

import com.chess.chessApp.controller.HomeController;
import com.chess.chessApp.controller.MainGameController;
import com.chess.chessApp.controller.RegistrationController;
import com.chess.chessApp.controller.SettingsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChessAppApplicationTests {

	@Autowired
	private HomeController homeController;

	@Autowired
	private RegistrationController registrationController;

	@Autowired
	private SettingsController settingsController;

	@Autowired
	private MainGameController mainGameController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() throws Exception {
		assertThat(homeController).isNotNull();
		assertThat(registrationController).isNotNull();
		assertThat(settingsController).isNotNull();
		assertThat(mainGameController).isNotNull();
	}
}
