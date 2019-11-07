<?php
defined('BASEPATH') OR exit('No direct script access allowed');

use Illuminate\Database\Eloquent\Model as Eloquent;

class Route extends Eloquent
{
    protected $table = "routes";

    /**
     * Route constructor.
     * @param bool $testMode
     */
    function __construct($testMode = false)
    {
        parent::__construct();
    	$ci =& get_instance();
    	$ci->load->model("user");
        if ($testMode) $this->table = "routes_tests";
   	}

    /**
     * @return User
     */
    public function user()
    {
    	return $this->hasOne("User", "id", "user_id");
    }

    /**
     * Get nearby routes based on lat and lng and trigonometry.
     *
     * @param $latitude
     * @param $longitude
     * @param array $count
     * @param int $distance
     * @return Route Collection
     */
    public static function getNearby($latitude, $longitude, $count = array(0, 10), $distance = 50)
    {
        $sql = /** @lang sql */
            "SELECT *, (
                    3959 * acos (
                      cos ( RADIANS($latitude) )
                      * cos ( RADIANS( start_lat ) )
                      * cos ( RADIANS( start_lng ) - RADIANS($longitude) )
                      + sin ( RADIANS($latitude) )
                      * sin ( RADIANS( start_lat ) )
                    )
                  ) AS distance
                FROM routes
                HAVING distance < $distance
                ORDER BY distance";
        if($count[0] != NULL) $sql .= " LIMIT $count[1], $count[0]";
        return self::hydrateRaw($sql);
    }

}