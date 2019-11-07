<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class Routes extends REST_Controller
{
    private $pageSize = 10;

    public function __construct()
    {
        parent::__construct();
        $this->load->helper("api_helper");
        $this->load->model("route");
    }

    /**
     * @param GET : count (0|10)
     * @param GET : coords (4.5234535|-2.325235)
     */
    public function all_get()
    {
        $nearby = false;
        $count = formatCount($this->get("count"));
        $coords = $this->get("coords");

        if (!empty($coords)) {
            $coords = formatCoords($coords);
            if ($coords != "0" and $coords != "0") {
                $routes = Route::getNearby($coords[0], $coords[1], $count);
                $allRoutesCount = Route::getNearby($coords[0], $coords[1], NULL);

                if ($routes->count() > 0) {
                    $nearby = true;
                    $this->response([
                        "status" => TRUE,
                        "count" => $routes->count(),
                        "pages" => intval(count($allRoutesCount) / $this->pageSize),
                        "routes" => $routes
                    ], REST_Controller::HTTP_OK);
                }
            }
        }

        if (!$nearby) {
            $routes = Route::take($this->pageSize)->offset($count[1])->get();
            $allRoutesCount = Route::all()->count();
            if ($routes->count() > 0) {
                $this->response([
                    "status" => TRUE,
                    "count" => $routes->count(),
                    "pages" => intval($allRoutesCount / $this->pageSize),
                    "routes" => $routes->toArray()
                ], REST_Controller::HTTP_OK);
            } else {
                $this->response([
                    "status" => FALSE,
                    "message" => "No Routes found"
                ], REST_Controller::HTTP_OK);
            }
        }
    }

    /**
     * @param $province (string)
     * @param GET : count (0|10)
     */
    public function province_get($province)
    {
        $count = formatCount($this->get("count"));
        $province = urldecode($province);
        $routes = Route::where("gmaps_province", "LIKE", "%$province%");
        $allRoutesCount = $routes->count();
        if ($allRoutesCount > 0) {
            $routes = $routes->take($this->pageSize)->offset($count[1])->get();
            $this->response([
                "status" => TRUE,
                "count" => $routes->count(),
                "pages" => intval($allRoutesCount / $this->pageSize),
                "routes" => $routes->toArray()
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                "status" => FALSE,
                "message" => "No Routes found"
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }

    /**
     * @param $id (int)
     */
    public function view_get($id)
    {
        $route = Route::find($id);
        if ($route->count() > 0) {
            $this->response([
                "status" => TRUE,
                "route" => $route->toArray()
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                "status" => FALSE,
                "message" => "No Route found"
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }

    /**
     * @param $search (string)
     * @param GET : count (0|10)
     */
    public function search_get($search)
    {
        $count = formatCount($this->get("count"));
        $search = urldecode($search);
        $routes = Route::where("equip_b_nombre", "LIKE", "%$search%")
            ->orWhere("equip_a_observaciones", "LIKE", "%$search%")
            ->orWhere("equip_b_acceso_modo", "LIKE", "%$search%")
            ->orWhere("gmaps_locality", "LIKE", "%$search%")
            ->orWhere("gmaps_province", "LIKE", "%$search%")
            ->orWhere("gmaps_street", "LIKE", "%$search%");
        $allRoutesCount = $routes->count();
        if ($allRoutesCount > 0) {
            if ($allRoutesCount > 1)
                $routes = $routes->take($this->pageSize)->offset($count[1])->get();
            else
                $routes = $routes->get();
            $this->response([
                "status" => TRUE,
                "count" => $routes->count(),
                "pages" => intval($allRoutesCount / $this->pageSize),
                "routes" => $routes->toArray()
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                "status" => FALSE,
                "message" => "No Routes found"
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }

    /**
     * @param GET : diff (int)
     * @param GET : province (string)
     * @param GET : count (0|10)
     */
    public function filter_get()
    {
        $count = formatCount($this->get("count"));

        $routes = Route::where("senda_dificultad", "LIKE", $this->get("diff"))
            ->where("gmaps_province", "LIKE", $this->get("province"));

        $allRoutesCount = $routes->count();
        if ($allRoutesCount > 0) {
            if ($allRoutesCount > 1)
                $routes = $routes->take($this->pageSize)->offset($count[1])->get();
            else
                $routes = $routes->get();
            $this->response([
                "status" => TRUE,
                "count" => $routes->count(),
                "pages" => intval($allRoutesCount / $this->pageSize),
                "routes" => $routes->toArray()
            ], REST_Controller::HTTP_OK);
        } else {
            $this->response([
                "status" => FALSE,
                "message" => "No Routes found"
            ], REST_Controller::HTTP_NOT_FOUND);
        }
    }


}