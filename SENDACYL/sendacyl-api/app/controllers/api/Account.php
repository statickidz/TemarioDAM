<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Account extends REST_Controller {

    /**
     * Account constructor.
     */
    public function __construct()
    {
        parent::__construct();
        $this->load->model("token");
        $this->load->model("user");
    }

    /**
     * Account information
     * @param GET token
     * @return object{status, object}
     */
    public function info_get($token)
    {
        $response = new stdClass();

        $token_entry = Token::getByValidToken($token);
        if($token_entry->count())
        {
            $user = $token_entry->first()->user()->first();
            $response->status = true;
            $response->user = new stdClass();
            $response->user->firstname = $user->firstname;
            $response->user->lastname = $user->lastname;
            $response->user->username = $user->username;
        }
        else
        {
            $response->status = false;
            $response->error = 'Token not found or session expired';
        }
        $this->response($response);
    }
        
    /**
     * Session check
     * @param POST token
     * @return object{status}
     */
    public function check_post() 
    {
        $response = new stdClass();
        $response->status=false;
        $token_entry = new Token();
        //Looking for valid token in DB
        $token_entry->getByValidToken($this->post('token'))->get();
        //Token found, session is ok
        if($token_entry->exists())
        {
            $user = $token_entry->user->get();
            $response->status=true;
            $response->is_admin = ($user->is_admin) ? TRUE : FALSE;
            $response->firstname = $user->firstname;
            $response->lastname = $user->lastname;
        }
        $this->response($response);
    }

    /**
     * Account login
     * @param POST username
     * @param POST password
     * @param POST client_secret_uuid
     * @return object{status, token}
     */
    public function login_post()
    {
        $response = new stdClass();
        //Parameters check
        $username = $this->post('username');
        $password = $this->post('password');
        $client_secret_uuid = $this->post('client_secret_uuid');
        if(!empty($username) && !empty($password) && !empty($client_secret_uuid))
        {
            $user = new User();
            $user->where('username', $this->post('username'))->where('password', sha1($this->post('password')))->get();
            //Record found
            if($user->exists()) 
            {
                $token = uniqid(md5(rand()), true);
                $token_entry = new Token();
                $token_entry->token = $token;
                $token_entry->user_id = $user->id;
                //Token expire after 1 year
                $token_entry->token_expire = time() + 60*60*24*365;
                $token_entry->client_secret_uuid = $this->post('client_secret_uuid');
                if($token_entry->save())
                {
                    $response->status = true;
                    $response->token = $token;
                }
                else
                {
                    $response->status = false;
                    $response->error = "Something wrong in creating Auth Token";
                }
            } 
            else
            {
                $response->status = false;
                $response->error = 'Username / Password wrong';
            }
        }
        else 
        {
            $response->status = false;
            $response->error = 'You must provide username, password and client_secret_uuid';
        }
        $this->response($response);
    }

    /**
     * Account logout
     * @param token
     * @return object{status}
     */
    public function logout_post()
    {
        $response = new stdClass();

        $token_entry = new Token();
        $token_entry->getByValidToken($this->post('token'))->get();
        if($token_entry->exists())
        {
            $token_entry->delete();
            $response->status=true;
        }
        else 
        {
           $response->status=false;
           $response->error='Token not found or session expired';
        }
        $this->response($response);
    }

    /**
     * Account edit
     * @param PUT token
     * @param PUT firstname
     * @param PUT lastname
     * @param PUT gitlab_private_key
     * @return object{status}
     */
    public function index_put()
    {
        $response = new stdClass();

        $token_entry = new Token();
        $token_entry->getByValidToken($this->put('token'))->get();
        if($token_entry->exists())
        {
            $user = new User();
            $user->get_by_id($token_entry->user_id);
            $user->firstname = $this->put('firstname');
            $user->lastname = $this->put('lastname');
            $user->gitlab_private_key = $this->put('gitlab_private_key');

            if($this->put('new_password')==$this->put('new_password_confirm')) 
            {
                //Check if password has been changed
                if($this->put('new_password')) 
                {
                   $user->password = sha1($this->put('new_password'));
                }
                //Try to save on db
                if($user->save()) 
                {
                    $response->status=true;
                }
                else
                {
                    $response->status=false;
                    $response->error='Account info not saved';
                }

            }
            else
            {
                $response->status=false;
                $response->error='Passwords did not match';
            }
        }
        else 
        {
           $response->status=false;
           $response->error='Token not found or session expired';
        }
        $this->response($response);
    }
        
    public function all_get($token)
    {
        $token_entry = new Token();
        $token_entry->getByValidToken($token)->get();
        if($token_entry->exists() && $token_entry->user->get()->is_admin)
        {
            $response = array();
            $users = new User();
            $users->order_by('username','DESC')->get();
            foreach($users as $user)
            {
                $u = new stdClass();
                $u->id = $user->id;
                $u->username = $user->username;
                $u->firstname = $user->firstname;
                $u->lastname = $user->lastname;
                $u->is_admin = $user->is_admin;
                array_push($response, $u);
            }
            $this->response($response);
        }
    }
    
    public function user_post()
    {
        $response = new stdClass();

        $token_entry = new Token();
        $token_entry->getByValidToken($this->post('token'))->get();
        if($token_entry->exists() && $token_entry->user->get()->is_admin)
        {
            $user = new User();
            $user->firstname = $this->post('firstname');
            $user->lastname = $this->post('lastname');
            $user->username = $this->post('username');
            $user->password = sha1($this->post('password'));
            //Try to save on db
            if($user->save()) 
            {
                $response->status=true;
            }
            else
            {
                $response->status=false;
                $response->error='User not saved';
            }
        }
        else 
        {
           $response->status=false;
           $response->error='Token not found, not an admin or session expired.';
        }
        $this->response($response);
    }
    
    public function user_put()
    {
        $response = new stdClass();

        $token_entry = new Token();
        $token_entry->getByValidToken($this->put('token'))->get();
        if($token_entry->exists() && $token_entry->user->get()->is_admin)
        {
            $user = new User();
            $user->get_by_id($this->put('id'));
            $user->firstname = $this->put('firstname');
            $user->lastname = $this->put('lastname');
            $user->username = $this->put('username');
            //Check if password has been changed
            if($this->put('new_password')) 
            {
               $user->password = sha1($this->put('new_password'));
            }
            //Try to save on db
            if($user->save()) 
            {
                $response->status=true;
            }
            else
            {
                $response->status=false;
                $response->error='User not updated';
            }
        }
        else 
        {
           $response->status=false;
           $response->error='Token not found, not an admin or session expired.';
        }
        $this->response($response);
    }
        
    public function user_get($id,$token)
    {
        $response = new stdClass();

        $token_entry = new Token();
        $token_entry->getByValidToken($token)->get();
        if($token_entry->exists() && $token_entry->user->get()->is_admin)
        {
            $user = new User();
            $user->get_by_id($id);
            $response->status = true;
            $response->user = new stdClass();
            $response->user->firstname = $user->firstname;
            $response->user->lastname = $user->lastname;
            $response->user->username = $user->username;
            $response->user->gitlab_private_key = $user->gitlab_private_key;
        }
        else
        {
            $response->status = false;
            $response->error = 'Token not found, not an admin or session expired';
        }
        $this->response($response);
    }
        
    public function user_delete($id, $token)
    {
        $token_entry = new Token();
        $token_entry->getByValidToken($token)->get();
        $response = new stdClass();
        if($token_entry->exists() && $token_entry->user->get()->is_admin)
        {
            if($token_entry->user_id!=$id)
            {
            $user = new User();
            $user->get_by_id($id);
            $user->delete();
            $response->status=TRUE;
            $this->response($response);
            }
            else 
            {
                $response->status=FALSE;
                $response->error='Cannot delete active user!';
                $this->response($response);
            }
        }
        else 
        {
            $response->status=FALSE;
            $response->error='Token not found, not an admin or session expired';
            $this->response($response);
        } 
    }
        
}