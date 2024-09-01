<?php
/**
 * Handles requests to the admin-ajax.php file.
 *
 * This file is part of the WordPress core and is used to handle all AJAX requests.
 *
 * @package WordPress
 */

// Silence is golden.
defined( 'ABSPATH' ) || exit;

// Initialize the AJAX hooks.
do_action( 'wp_ajax_init' );

// Get the requested action.
if ( ! empty( $_REQUEST['action'] ) ) {
    $action = $_REQUEST['action'];
} elseif ( ! empty( $_REQUEST['acf_action'] ) ) { // ACF-specific
    $action = $_REQUEST['acf_action'];
} else {
    die( 'no action provided' );
}

// Verify the nonce for security.
if ( ! wp_verify_nonce( $_REQUEST['_wpnonce'], $action ) ) {
    die( 'Nonce verification failed' );
}

// Check user permissions.
if ( ! current_user_can( 'edit_posts' ) && ! current_user_can( 'edit_pages' ) && ! ( defined( 'DOING_AJAX' ) && DOING_AJAX ) ) {
    die( 'You do not have sufficient permissions to access this page.' );
}

// Call the appropriate hook based on the action.
do_action( 'wp_ajax_' . $action );
do_action( 'wp_ajax_nopriv_' . $action );

// Exit after processing the action.
wp_die();
?>
