/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.models

import org.openapitools.client.models.CategoryJsonldCategoryReadCategoryItemReadContext

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param user 
 * @param name 
 * @param atContext 
 * @param atId 
 * @param atType 
 * @param id 
 * @param friends 
 * @param obtainedRewards 
 */


data class PlayerjsonldPlayerreadPlayeritemread (

    @Json(name = "user")
    val user: kotlin.String?,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "@context")
    val atContext: CategoryJsonldCategoryReadCategoryItemReadContext? = null,

    @Json(name = "@id")
    val atId: kotlin.String? = null,

    @Json(name = "@type")
    val atType: kotlin.String? = null,

    @Json(name = "id")
    val id: kotlin.Int? = null,

    @Json(name = "friends")
    val friends: kotlin.collections.List<PlayerjsonldPlayerreadPlayeritemread>? = null,

    @Json(name = "obtainedRewards")
    val obtainedRewards: kotlin.collections.List<kotlin.String>? = null

) {


}
