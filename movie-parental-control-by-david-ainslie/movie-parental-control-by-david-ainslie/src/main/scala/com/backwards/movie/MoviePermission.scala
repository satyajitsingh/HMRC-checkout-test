package com.backwards.movie

trait MoviePermission

case object Permitted extends MoviePermission

case object Restricted extends MoviePermission