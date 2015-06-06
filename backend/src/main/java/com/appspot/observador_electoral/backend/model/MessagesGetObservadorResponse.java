/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-06-05 at 17:09:58 UTC 
 * Modify at your own risk.
 */

package com.appspot.observador_electoral.backend.model;

/**
 * Response to observador information request ok: (Boolean) User search successful or failed error:
 * (String) If search failed, contains the reason, otherwise empty. email = (String) name = (String)
 * age = (Integer) account_type = (String) installation_id = (String) Parse ID for Push
 * notifications
 * <p/>
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the backend. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MessagesGetObservadorResponse extends com.google.api.client.json.GenericJson {

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key("account_type")
    private java.lang.String accountType;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key
    @com.google.api.client.json.JsonString
    private java.lang.Long age;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key
    private java.lang.String email;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key
    private java.lang.String error;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key("installation_id")
    private java.lang.String installationId;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key
    private java.lang.String name;

    /**
     * The value may be {@code null}.
     */
    @com.google.api.client.util.Key
    private java.lang.Boolean ok;

    /**
     * @return value or {@code null} for none
     */
    public java.lang.String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType accountType or {@code null} for none
     */
    public MessagesGetObservadorResponse setAccountType(java.lang.String accountType) {
        this.accountType = accountType;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.Long getAge() {
        return age;
    }

    /**
     * @param age age or {@code null} for none
     */
    public MessagesGetObservadorResponse setAge(java.lang.Long age) {
        this.age = age;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * @param email email or {@code null} for none
     */
    public MessagesGetObservadorResponse setEmail(java.lang.String email) {
        this.email = email;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.String getError() {
        return error;
    }

    /**
     * @param error error or {@code null} for none
     */
    public MessagesGetObservadorResponse setError(java.lang.String error) {
        this.error = error;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.String getInstallationId() {
        return installationId;
    }

    /**
     * @param installationId installationId or {@code null} for none
     */
    public MessagesGetObservadorResponse setInstallationId(java.lang.String installationId) {
        this.installationId = installationId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * @param name name or {@code null} for none
     */
    public MessagesGetObservadorResponse setName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public java.lang.Boolean getOk() {
        return ok;
    }

    /**
     * @param ok ok or {@code null} for none
     */
    public MessagesGetObservadorResponse setOk(java.lang.Boolean ok) {
        this.ok = ok;
        return this;
    }

    @Override
    public MessagesGetObservadorResponse set(String fieldName, Object value) {
        return (MessagesGetObservadorResponse) super.set(fieldName, value);
    }

    @Override
    public MessagesGetObservadorResponse clone() {
        return (MessagesGetObservadorResponse) super.clone();
    }

}