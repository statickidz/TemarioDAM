<?php
defined('BASEPATH') OR exit('No direct script access allowed');

use \Illuminate\Database\Eloquent\Model as Eloquent;

class Token extends Eloquent
{
    protected $table = "tokens";

    /**
     * Token constructor.
     */
    function __construct()
    {
        parent::__construct();
    	$ci =& get_instance();
    	$ci->load->model("user");
   	}

    /**
     * @return User
     */
    public function user()
    {
    	return $this->hasOne("User", "id", "user_id");
    }

    /**
     * @param $token
     * @return \Illuminate\Database\Eloquent\Collection
     */
    public function scopeGetByValidToken($query, $token)
    {
        return $query->whereRaw("SHA1(CONCAT(token,client_secret_uuid))='"
            .$token."' AND token_expire > UNIX_TIMESTAMP(CURRENT_TIMESTAMP)");
    }
}