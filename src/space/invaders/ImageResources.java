/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space.invaders;

/**
 *
 * @author Frankie
 */
public enum ImageResources {
	ALIEN1("./assets/images/alien1.png"),
	ALIEN2("./assets/images/alien2.png"),
	ALIEN3("./assets/images/aliens3.png"),
	SPACESHIP("./assets/images/spaceship.png"),
	SHIELD("./assets/images/shield.png");

	private final String url;

	private ImageResources(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}
}
