/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.auth;

import org.apache.shiro.config.Ini;
import org.apache.shiro.env.BasicIniEnvironment;

/**
 *
 * @author User
 */
public class BhaashikIniEnvironment extends BasicIniEnvironment {

    public BhaashikIniEnvironment(Ini ini) {
        super(ini);
    }

    public BhaashikIniEnvironment(String iniResourcePath) {
        super(iniResourcePath);
    }
    
}
