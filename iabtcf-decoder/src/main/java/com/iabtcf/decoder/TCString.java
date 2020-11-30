package com.iabtcf.decoder;

/*-
 * #%L
 * IAB TCF Core Library
 * %%
 * Copyright (C) 2020 IAB Technology Laboratory, Inc
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.iabtcf.exceptions.ByteParseException;
import com.iabtcf.exceptions.TCStringDecodeException;
import com.iabtcf.exceptions.UnsupportedVersionException;
import com.iabtcf.utils.IntIterable;
import com.iabtcf.v2.PublisherRestriction;
import org.threeten.bp.Instant;

import java.util.List;

public abstract class TCString {

    /**
     * Decodes an iabtcf compliant encoded string.
     *
     * @throws ByteParseException if version field failed to parse
     * @throws UnsupportedVersionException invalid version field
     * @throws IllegalArgumentException if consentString is not in valid Base64 scheme
     */
    static TCString decode(String consentString, DecoderOption... options)
            throws IllegalArgumentException, ByteParseException, UnsupportedVersionException {
        return TCStringDecoder.decode(consentString, options);
    }

    /**
     * Version number of the encoding format
     *
     * @return the version number
     * @throws TCStringDecodeException
     * @since 1.0
     */
    int getVersion() {
        return 0;
    }

    /**
     * Epoch deciseconds (0.1 of a second) when this TC String was first created
     *
     * @return timestamp the record was first created
     * @throws TCStringDecodeException
     * @since 1.0
     */
    Instant getCreated() {
        return Instant.MIN;
    }

    /**
     * Epoch deciseconds (0.1 of a second) when TC String was last updated
     *
     * @return timestamp record was last updated
     * @throws TCStringDecodeException
     * @since 1.0
     */
    Instant getLastUpdated() {
        return Instant.MAX;
    }

    /**
     * Consent Management Platform ID that last updated the TC String
     *
     * @return the Consent Management Platform ID
     * @throws TCStringDecodeException
     * @since 1.0
     */
    int getCmpId() {
        return 0;
    }

    /**
     * Consent Management Platform version of the CMP that last updated this TC String
     *
     * @return version of the Consent Management Platform that updated this record
     * @throws TCStringDecodeException
     * @since 1.0
     */
    int getCmpVersion() {
        return 0;
    }

    /**
     * CMP Screen number at which consent was given for a user with the CMP that last updated this
     * TC String.
     * <p>
     * The number is a CMP internal designation and is CmpVersion specific. The number is used for
     * identifying on which screen a user gave consent as a record.
     *
     * @return the screen number identifier
     * @throws TCStringDecodeException
     * @since 1.0
     */
    int getConsentScreen() {
        return 0;
    }

    /**
     * Two-letter ISO 639-1 language code in which the CMP UI was presented.
     *
     * @return the language string
     * @throws TCStringDecodeException
     * @since 1.0
     */
    String getConsentLanguage() {
        return "AA";
    }

    /**
     * Number corresponds to GVL vendorListVersion. Version of the GVL used to create this TC
     * String.
     *
     * @return the version number
     * @throws TCStringDecodeException
     * @since 1.0
     */
    int getVendorListVersion() {
        return 0;
    }

    /**
     * The user’s consent value for each Purpose established on the legal basis of consent. The
     * Purposes are numerically identified and published in the Global Vendor List.
     * <p>
     * An alias for PurposesAllowed
     *
     * @return The integer values for each established Purpose.
     * @throws TCStringDecodeException
     * @since 1.0
     */
    IntIterable getPurposesConsent() {
        return null;
    }

    /**
     * The vendor identifiers that have consent to process this users personal data. The vendor
     * identifiers are published in the GVL.
     *
     * @return the vendor identifiers.
     * @throws TCStringDecodeException
     * @since 1.0
     */
    IntIterable getVendorConsent() {
        return null;
    }

    /**
     * Default consent for VendorIds not covered by a RangeEntry. VendorIds covered by a RangeEntry
     * have a consent value the opposite of DefaultConsent.
     * <p>
     * This field is not used by Transparency and Consent String v2.0 specifications and always
     * returns false.
     *
     * @return all vendors that have consent to process this users personal data
     * @throws TCStringDecodeException
     * @since 1.0
     */
    boolean getDefaultVendorConsent() {
        return false;
    }

    /**
     * From the corresponding field in the GVL that was used for obtaining consent. A new policy
     * version invalidates existing strings and requires CMPs to re-establish transparency and
     * consent from users.
     *
     * @return version of policy used within GVL
     * @throws TCStringDecodeException
     * @since 2.0
     */
    int getTcfPolicyVersion() {
        return 0;
    }

    /**
     * Whether the signals encoded in this TC String were from service-specific storage versus
     * global consesu.org shared storage.
     *
     * @return if signals are service-specific or global
     * @throws TCStringDecodeException
     * @since 2.0
     */
    boolean isServiceSpecific() {
        return false;
    }

    /**
     * Setting this to field to true means that a publisher-run CMP – that is still IAB Europe
     * registered – is using customized Stack descriptions and not the standard stack descriptions
     * defined in the Policies (Appendix A section E). A CMP that services multiple publishers sets
     * this value to false.
     *
     * @return true if if the CMP used non-IAB standard stacks during consent gathering; false
     * otherwise.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    boolean getUseNonStandardStacks() {
        return false;
    }

    /**
     * The TCF Policies designates certain Features as "special" which means a CMP must afford the
     * user a means to opt in to their use. These "Special Features" are published and numerically
     * identified in the Global Vendor List separately from normal Features.
     *
     * @return the Special Features the Vendor may utilize when performing some declared Purposes
     * processing.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getSpecialFeatureOptIns() {
        return null;
    }

    /**
     * The Purpose’s transparency requirements are met for each Purpose on the legal basis of
     * legitimate interest and the user has not exercised their "Right to Object" to that Purpose.
     * <p>
     * By default or if the user has exercised their "Right to Object" to a Purpose, the
     * corresponding identifier for that Purpose is set to false.
     *
     * @return The purpose identifiers for which the legal basis of legitimate interest are
     * established.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getPurposesLITransparency() {
        return null;
    }

    /**
     * CMPs can use the PublisherCC field to indicate the legal jurisdiction the publisher is under
     * to help vendors determine whether the vendor needs consent for Purpose 1.
     * <p>
     * In a globally-scoped TC string, this field must always have a value of false. When a CMP
     * encounters a globally-scoped TC String with PurposeOneTreatment set to true then it is
     * considered invalid and the CMP must discard it and re-establish transparency and consent.
     *
     * @return true if Purpose 1 was NOT disclosed; false otherwise.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    boolean getPurposeOneTreatment() {
        return false;
    }

    /**
     * The country code of the country that determines legislation of reference. Commonly, this
     * corresponds to the country in which the publisher’s business entity is established.
     *
     * @return ISO 3166-1 alpha-2 country code
     * @throws TCStringDecodeException
     * @since 2.0
     */
    String getPublisherCC() {
        return "AA";
    }

    /**
     * If a user exercises their "Right To Object" to a vendor’s processing based on a legitimate
     * interest.
     *
     * @return vendor identifiers that can process this user based on legitimate interest
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getVendorLegitimateInterest() {
        return null;
    }

    /**
     * The restrictions of a vendor's data processing by a publisher within the context of the users
     * trafficking their digital property.
     *
     * @return the list of publisher restrictions.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    List<PublisherRestriction> getPublisherRestrictions() {
        return null;
    }

    /**
     * Part of the OOB segments expressing that a Vendor is using legal bases outside of the TCF to
     * process personal data.
     *
     * @return A list of Vendors that the publisher allows to use out-of-band legal bases.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getAllowedVendors() {
        return null;
    }

    /**
     * Part of the OOB segments expressing that a Vendor is using legal bases outside of the TCF to
     * process personal data.
     *
     * @return A list of Vendors that disclosed to the user.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getDisclosedVendors() {
        return null;
    }

    /**
     * Part of the Publisher Transparency and Consent segment of a TC String that publishers may use
     * to establish transparency with and receive consent from users for their own legal bases to
     * process personal data or to share with vendors if they so choose.
     * <p>
     * The user's consent value for each Purpose established on the legal basis of consent, for the
     * publisher
     * <p>
     * The Purposes are numerically identified and published in the Global Vendor List.
     *
     * @return the consent value for each Purpose
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getPubPurposesConsent() {
        return null;
    }

    /**
     * Part of the Publisher Transparency and Consent segment of a TC String that publishers may use
     * to establish transparency with and receive consent from users for their own legal bases to
     * process personal data or to share with vendors if they so choose.
     * <p>
     * The Purpose’s transparency requirements are met for each Purpose established on the legal
     * basis of legitimate interest and the user has not exercised their "Right to Object" to that
     * Purpose.
     * <p>
     * By default or if the user has exercised their "Right to Object" to a Purpose, the
     * corresponding identifier for that Purpose is set to false.
     *
     * @return The consent value for each Purpose where legitimate interest was established.
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getPubPurposesLITransparency() {
        return null;
    }

    /**
     * Part of the Publisher Transparency and Consent segment of a TC String that publishers may use
     * to establish transparency with and receive consent from users for their own legal bases to
     * process personal data or to share with vendors if they so choose.
     * <p>
     * Custom purposes will be defined by the publisher and displayed to a user in a CMP user
     * interface.
     *
     * @return The established custom purpose consent values
     * @throws TCStringDecodeException
     * @since 2.0
     */
    IntIterable getCustomPurposesConsent() {
        return null;
    }

    /**
     * Part of the Publisher Transparency and Consent segment of a TC String that publishers may use
     * to establish transparency with and receive consent from users for their own legal bases to
     * process personal data or to share with vendors if they so choose.
     *
     * @return The custom purpose consent values with established legitimate interest disclosure.
     * @throws TCStringDecodeException
     */
    IntIterable getCustomPurposesLITransparency() {
        return null;
    }
}
