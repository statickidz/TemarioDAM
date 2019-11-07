<?php
defined('BASEPATH') OR exit('No direct script access allowed');

use \Illuminate\Database\Eloquent\Model as Eloquent;

class User extends Eloquent
{
    protected $table = "users";

    /**
     * User constructor.
     */
    function __construct()
    {
        parent::__construct();
    	$ci =& get_instance();
    	$ci->load->model("token");
    	$ci->load->model("route");
   	}

    /**
     * @return Token
     */
    public function tokens()
    {
    	return $this->hasMany("Token", "id", "id");
    }

    /**
     * @return Route Collection
     */
    public function routes()
    {
    	return $this->hasMany("Route", "user_id", "id");
    }

}