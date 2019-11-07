<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Landing extends CI_Controller
{

    public function index()
    {
        //$this->load->view('landing/index');
    }

    public function test()
    {
    	$this->load->model("route");
    	$this->load->model("user");

    	$user = User::where("username", "LIKE", "admin")->first();

    	$routes = Route::getNearby("41.7682349", "-2.4678581");
    	echo '<pre>';
    	var_dump($user->tokens()->get()->first());
    	echo '</pre>';
    }
}
