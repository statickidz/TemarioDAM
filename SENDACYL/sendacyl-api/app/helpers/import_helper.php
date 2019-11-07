<?php
defined('BASEPATH') OR exit('No direct script access allowed');

if (!function_exists('uploadFileFromUrl'))
{
    /**
     * Upload file to public folder.
     *
     * @param $url
     * @param $folder
     * @param $filename
     */
    function uploadFileFromUrl($url, $folder, $filename)
	{
		$path = "./public/".$folder."/".$filename;
		if(!file_exists($path) && isUrlExist($url))
			copy($url, $path);
	}
}

if (!function_exists('isUrlExist'))
{
    /**
     * Check if a URL exists and its 200 code.
     *
     * @param $url
     * @return bool
     */
    function isUrlExist($url)
	{
		$ch = curl_init($url);    
	    curl_setopt($ch, CURLOPT_NOBODY, true);
	    curl_exec($ch);
	    $code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	    if($code == 200){
	    	$status = true;
	    } else {
	    	$status = false;
	    }
	    curl_close($ch);
		return $status;
	}
}

if (!function_exists('generateKmlFile'))
{
    /**
     * Generate and save KML file with custom Google template.
     *
     * @param $placemark (XML Tag)
     * @param $routeId
     */
    function generateKmlFile($placemark, $routeId)
	{

		$routeKml = $placemark->asXML();
		if(getGeometryType($placemark) == 'LineString')
			$routeKml = $placemark->LineString->asXML();
		if(getGeometryType($placemark) == 'MultiGeometry')
			$routeKml = $placemark->MultiGeometry->asXML();

		$headTpl =
		'<?xml version="1.0" encoding="UTF-8"?>
			<kml xmlns="http://www.opengis.net/kml/2.2">
			  <Document>
			    <name>KmlFile</name>
			    <Style id="sendacyl">
			      <LineStyle>
			        <width>7</width>
			        <color>E64eadf0</color>
			      </LineStyle>
			      <PolyStyle>
			        <color>7d4eadf0</color>
			      </PolyStyle>
			    </Style>
			    <Folder>
			      <name>SendaCYL Route</name>
			      <visibility>1</visibility>
			      <description>SendaCYL Route</description>
			      <Placemark>
			        <name>SendaCYL Route</name>
			        <visibility>1</visibility>
			        <styleUrl>#sendacyl</styleUrl>
									        ';
		$footerTpl = '</Placemark>
			    </Folder>
			  </Document>
			</kml>';

		$domXml = new DOMDocument("1.0", "UTF-8");
		$domXml->preserveWhiteSpace = false;
		$domXml->formatOutput = true;
		$domXml->loadXML($headTpl.$routeKml.$footerTpl);
		$domXml->save("./public/kml/".$routeId.".kml");
	}
}

if (!function_exists('isValidRoute'))
{
    /**
     * Check if a route is valid based on placemark data.
     *
     * @param $placemark (XML Tag)
     * @return bool
     */
    function isValidRoute($placemark)
	{
		$lineString = false;
		$multiGeometry = false;
		if($placemark->LineString->asXML() != "") {
			$lineString = true;
		} else {
			if($placemark->MultiGeometry->asXML() != "") {
				$multiGeometry = true;
			}
		}

		return ($lineString || $multiGeometry);
	}
}


if (!function_exists('getGeometryType'))
{
    /**
     * Returns if a placemark is LineString or MultiGeometry type.
     *
     * @param $placemark (XML Tag)
     * @return string (LineString OR MultiGeometry)
     */
    function getGeometryType($placemark)
	{
		$lineString = false;
		$multiGeometry = false;
		if($placemark->LineString->asXML() != "") {
			$lineString = true;
		} else {
			if($placemark->MultiGeometry->asXML() != "") {
				$multiGeometry = true;
			}
		}

		if($lineString) return "LineString";
		if($multiGeometry) return "MultiGeometry";
	}
}

if (!function_exists('getStartCoordinates'))
{
    /**
     * Returns start lat,lng from a placemark.
     *
     * @param $placemark (XML Tag)
     * @return mixed
     */
    function getStartCoordinates($placemark)
	{
		$lineString = false;
		$multiGeometry = false;
		if($placemark->LineString->asXML() != "") {
			$lineString = true;
		} else {
			if($placemark->MultiGeometry->asXML() != "") {
				$multiGeometry = true;
			}
		}

		$coordinates = "0,0";
		if($lineString)
			$coordinates = (string) $placemark->LineString->coordinates->asXML();
		if($multiGeometry)
			$coordinates = (string) $placemark->MultiGeometry->LineString->coordinates->asXML();

		$coordinatesParts = explode(",", $coordinates);

		return str_replace(" ", ",", $coordinatesParts[1]);
	}
}


if (!function_exists('getGoogleMapsLocation'))
{
    /**
     * Scrape Google Maps data from coordinates.
     *
     * @param $coordinates
     * @return stdClass
     */
    function getGoogleMapsLocation($coordinates)
	{
		$gMaps = new stdClass();

		$apiKey = "AIzaSyBivcfAp6vAbdeTM4gPGVWQZXoxFzV3P8A";
		$url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=".$coordinates."&key=".$apiKey."&result_type=street_address";
		$json = file_get_contents($url);
		$data = json_decode($json);

		if($data->status == "OK") {
			for($i=0; $i<count($data->results[0]->address_components); $i++) {
				$current = $data->results[0]->address_components[$i];

				if($current->types[0] == 'administrative_area_level_1') {
					$gMaps->region = $current->long_name;
				}

				if($current->types[0] == 'administrative_area_level_2') {
					$gMaps->province = $current->long_name;
				}

				if($current->types[0] == 'locality') {
					$gMaps->locality = $current->long_name;
				}

				if($current->types[0] == 'country') {
					$gMaps->country = $current->long_name;
				}

				if($current->types[0] == 'route') {
					$gMaps->street = $current->long_name;
				}
				
			}

			$gMaps->lat = $data->results[0]->geometry->location->lat;
			$gMaps->lng = $data->results[0]->geometry->location->lng;

			$url = "http://maps.google.com/cbk?output=xml&v=4&dm=0&pm=0&ll=".$coordinates."&radius=1000&hl=es";
			$xml = file_get_contents($url);
			$data = simplexml_load_string($xml);

			foreach ($data->data_properties->attributes() as $key => $value) {
				switch ($key) {
					case 'pano_id':
						$gMaps->pano_id = (string) $value;
						break;
				}
			}
		} else {
			$url = "http://maps.google.com/cbk?output=xml&v=4&dm=0&pm=0&ll=".$coordinates."&radius=4000&hl=es";
			$xml = file_get_contents($url);
			$data = simplexml_load_string($xml);
			$region = (string) $data->data_properties->region;
			foreach ($data->data_properties->attributes() as $key => $value) {
				switch ($key) {
					case 'pano_id':
						$gMaps->pano_id = (string) $value;
						break;
				}
			}

			$url = "https://maps.googleapis.com/maps/api/geocode/json?address=".urlencode($region)."&key=".$apiKey."&result_type=street_address";
			$json = file_get_contents($url);
			$data = json_decode($json);

			if($data->status == "OK") {
                for($i=0; $i<count($data->results[0]->address_components); $i++) {
					$current = $data->results[0]->address_components[$i];

					if($current->types[0] == 'administrative_area_level_1') {
						$gMaps->region = $current->long_name;
					}

					if($current->types[0] == 'administrative_area_level_2') {
						$gMaps->province = $current->long_name;
					}

					if($current->types[0] == 'locality') {
						$gMaps->locality = $current->long_name;
					}

					if($current->types[0] == 'country') {
						$gMaps->country = $current->long_name;
					}

					if($current->types[0] == 'route') {
						$gMaps->street = $current->long_name;
					}
					
				}

				$gMaps->lat = $data->results[0]->geometry->location->lat;
				$gMaps->lng = $data->results[0]->geometry->location->lng;

			}

		}

		return $gMaps;
	}
}
