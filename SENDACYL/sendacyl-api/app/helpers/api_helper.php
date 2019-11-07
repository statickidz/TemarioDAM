<?php
defined('BASEPATH') OR exit('No direct script access allowed');

if (!function_exists('formatCount'))
{
    function formatCount($count)
    {
        $count = urldecode($count);
        $limitOffset = explode("|", $count);
        $offset = 0;
        $limit = 10;
        if(isset($limitOffset[1])) {
            $limit = $limitOffset[1]-($limitOffset[0]);
            $offset = $limitOffset[0];
        } else {
            $offset = 0;
            $limit = $count;
        }

        return array($limit, $offset);
        /*$count = explode("|", $count);
        return array($count[0], $count[1]);*/

    }
}


if (!function_exists('formatCoords'))
{
    function formatCoords($coordinates)
    {
        //$coordinates = urldecode($coordinates);
        $coords = explode("|", $coordinates);
        return array($coords[0], $coords[1]);
    }
}