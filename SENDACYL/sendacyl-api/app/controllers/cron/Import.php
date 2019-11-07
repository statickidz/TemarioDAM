<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Import extends CI_Controller
{
    var $REMOTE_KML_FILE_URI
        = "http://www.datosabiertos.jcyl.es/web/jcyl/risp/es/cartografia/sendas/1284378322994.kml";
    var $KML_FILE_PATH = "./public/import/senda.kml";
    var $KML_BACKUP_PATH = "./public/import/backup/";
    var $IMAGE_PATH = "http://www.idecyl.jcyl.es/sigren/espacioimg/";
    var $IMAGE_EXT = ".jpg";

    /**
     * Import constructor.
     */
    public function __construct()
    {
        parent::__construct();
        $this->load->model("route");
        $this->load->helper("import_helper");
    }


    /**
     * Import all routes from KML file.
     *
     * This function should run one time, then use update()
     * to update existing routes, otherwise this will
     * duplicate routes in order, so BE CAREFULLY!
     *
     */
    public function all()
    {

        $kml = simplexml_load_file($this->REMOTE_KML_FILE_URI);
        $kmlRoutes = $kml->Document->Folder->Placemark;

        $cont = 0;
        foreach ($kmlRoutes as $r) {
            if (isValidRoute($r)) {
                $route = new Route();
                $route["placemark_id"] = (string)$r->attributes()->id;

                foreach ($r->ExtendedData->SchemaData->SimpleData as $data) {
                    switch ($data->attributes()) {
                        case "atr_gr_id":
                            $route["atr_gr_id"] = (string)$data;
                            break;
                        case "atr_gr_tipo":
                            $route["atr_gr_tipo"] = (string)$data;
                            break;
                        case "atr_gr_visible":
                            $route["atr_gr_visible"] = (string)$data;
                            break;
                        case "atr_gr_tiene_q":
                            $route["atr_gr_tiene_q"] = (string)$data;
                            break;
                        case "atr_gr_alerta":
                            $route["atr_gr_alerta"] = (string)$data;
                            break;
                        case "equip_a_codigo":
                            $route["equip_a_codigo"] = (string)$data;
                            break;
                        case "equip_a_observaciones":
                            $route["equip_a_observaciones"] = (string)$data;
                            break;
                        case "equip_a_estado_fecha":
                            $route["equip_a_estado_fecha"] = date("Y-m-d H:i:s", strtotime((string)$data));
                            break;
                        case "equip_a_fecha_declaracion":
                            $route["equip_a_fecha_declaracion"] = date("Y-m-d H:i:s", strtotime((string)$data));
                            break;
                        case "equip_b_senalizacion_ext":
                            $route["equip_b_senalizacion_ext"] = (string)$data;
                            break;
                        case "equip_b_acceso_modo":
                            $route["equip_b_acceso_modo"] = (string)$data;
                            break;
                        case "equip_b_nombre":
                            $route["equip_b_nombre"] = (string)$data;
                            break;
                        case "cerramiento_id":
                            $route["cerramiento_id"] = (string)$data;
                            break;
                        case "acc_id":
                            $route["acc_id"] = (string)$data;
                            break;
                        case "acc_dis_id":
                            $route["acc_dis_id"] = (string)$data;
                            break;
                        case "equip_b_tiene_interes":
                            $route["equip_b_tiene_interes"] = (string)$data;
                            break;
                        case "equip_b_superficie_aprox":
                            $route["equip_b_superficie_aprox"] = (string)$data;
                            break;
                        case "cerramiento_material_id":
                            $route["cerramiento_material_id"] = (string)$data;
                            break;
                        case "web":
                            $route["web"] = (string)$data;
                            break;
                        case "senda_tipo":
                            $route["senda_tipo"] = (string)$data;
                            break;
                        case "senda_longitud":
                            $route["senda_longitud"] = (string)$data;
                            break;
                        case "senda_tiempo_recorrido":
                            $route["senda_tiempo_recorrido"] = (string)$data;
                            break;
                        case "senda_ciclabilidad":
                            $route["senda_ciclabilidad"] = (string)$data;
                            break;
                        case "senda_codigo":
                            $route["senda_codigo"] = (string)$data;
                            break;
                        case "senda_dificultad":
                            $route["senda_dificultad"] = (string)$data;
                            break;
                        case "senda_desnivel":
                            $route["senda_desnivel"] = (string)$data;
                            break;
                        case "tipo_oficial":
                            $route["tipo_oficial"] = (string)$data;
                            break;
                    }
                }

                $startCoordinates = getStartCoordinates($r);
                $latLng = explode(",", $startCoordinates);
                $gMaps = getGoogleMapsLocation($startCoordinates);
                $route["gmaps_street"] = ($gMaps->street != null) ? $gMaps->street : "";
                $route["gmaps_locality"] = ($gMaps->locality != null) ? $gMaps->locality : "";
                $route["gmaps_province"] = ($gMaps->province != null) ? $gMaps->province : "";
                $route["gmaps_region"] = ($gMaps->region != null) ? $gMaps->region : "";
                $route["gmaps_country"] = ($gMaps->country != null) ? $gMaps->country : "";
                $route["gmaps_lat"] = ($gMaps->lat != null) ? $gMaps->lat : "";
                $route["gmaps_lng"] = ($gMaps->lng != null) ? $gMaps->lng : "";
                $route["gmaps_pano_id"] = ($gMaps->pano_id != null) ? $gMaps->pano_id : "";

                $route["start_lat"] = $latLng[0];
                $route["start_lng"] = $latLng[1];

                $routeId = $this->routes_model->add($route);
                if ($routeId) {
                    // Upload image
                    $code = $route["equip_a_codigo"];
                    if ($code != null && $code != "") {
                        $imageCode = substr($code, 0, 3);
                        uploadFileFromUrl($this->IMAGE_PATH . $imageCode . $this->IMAGE_EXT, 'img', $imageCode . $this->IMAGE_EXT);
                    }

                    // Generate and upload KML
                    generateKmlFile($r, $routeId);
                }

                $route->save();

                echo '<pre>';
                var_dump($route);
                echo '</pre>';
                echo '<br><br><br>';

                $cont++;
                //if ($cont == 1) break;

            } # end isValidRoute

        }  # end foreach routes
    }

    /**
     * Fix Google Maps data on routes that does not have data.
     *
     */
    public function update_location_data()
    {
        $routes = Route::where("gmaps_province", "=", "")
            ->orWhereNull("gmaps_province");

        foreach ($routes->get() as $route) {

            //Getting data from existing coordinates
            $gMaps = getGoogleMapsLocation($route->start_lat . "," . $route->start_lng);
            if (!isset($gMaps->locality)) {

                //Check if first attempt has success
                if (isset($gMaps->lat) && isset($gMaps->lng)) {
                    $gMaps2 = getGoogleMapsLocation($gMaps->lat . "," . $gMaps->lng);

                    //Check again to final data
                    if (isset($gMaps2->locality)) {

                        $route->gmaps_street = ($gMaps2->street != null) ? $gMaps2->street : "";
                        $route->gmaps_locality = ($gMaps2->locality != null) ? $gMaps2->locality : "";
                        $route->gmaps_province = ($gMaps2->province != null) ? $gMaps2->province : "";
                        $route->gmaps_region = ($gMaps2->region != null) ? $gMaps2->region : "";
                        $route->gmaps_country = ($gMaps2->country != null) ? $gMaps2->country : "";
                        $route->gmaps_lat = ($gMaps2->lat != null) ? $gMaps2->lat : "";
                        $route->gmaps_lng = ($gMaps2->lng != null) ? $gMaps2->lng : "";
                        $route->gmaps_pano_id = ($gMaps2->pano_id != null) ? $gMaps2->pano_id : "";

                        $route->save();

                    } # end 3 check

                } # end 2 check

            } # end 1 check

        }
    }

    /**
     *  Automated function to bulk update all line style colors.
     *
     */
    public function update_line_style()
    {
        // Color Converter -> http://www.netdelight.be/kml/
        // OLD ORANGE KML -> ff4eadf0
        // NEW ORANGE KML -> ff0098ff
        // GREEN KML -> ff2e8900

        $newWidth = 9;
        $newColor = "ff0098ff";
        $routes = Route::all();

        foreach ($routes as $route) {
            $id = $route->id;

            $dom = new DOMDocument();
            $dom->load("./public/kml/$id.kml");

            $root = $dom->documentElement;

            $widthTags = $root->getElementsByTagName('width');
            foreach ($widthTags as $widthTag) {
                $widthTag->nodeValue = $newWidth;
            }

            $colorTags = $root->getElementsByTagName('color');
            foreach ($colorTags as $colorTag) {
                $colorTag->nodeValue = $newColor;
            }

            $dom->saveXML();
            $dom->save("./public/kml/$id.kml");
        }

    }

    /**
     *  Cron to update existing routes with new file.
     *
     */
    public function update()
    {
        // TODO
        // 1 - read for updated file
        // 2 - check if routes exists with 'atr_gr_id' field
        // 3 - update or create routes
    }

    /**
     *  Cron to upload and backup a new fresh updated file.
     *
     */
    public function upload()
    {
        if ($this->_backupExisting()) {
            $newFile = "./public/import/senda.kml";
            if (copy($this->REMOTE_KML_FILE_URI, $newFile)) {
                echo "Upload success!";
            } else {
                echo "Upload failed.";
            }
        }
    }

    /**
     * Backup existing KML file and upload new one.
     *
     * @return bool
     */
    private function _backupExisting()
    {
        $date = new DateTime();
        $date->modify('-1 week');
        $dateString = $date->format("d-m-Y");

        $newFile = $this->KML_BACKUP_PATH . "senda-" . $dateString . ".kml";

        if (copy($this->KML_FILE_PATH, $newFile)) {
            if (unlink($this->KML_FILE_PATH))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }


}
