/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.api.interactions.components.text;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.data.SerializableData;
import net.dv8tion.jda.internal.interactions.component.ModalImpl;
import net.dv8tion.jda.internal.utils.Checks;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * Represents a Discord Modal
 *
 * <p>Replying to an interaction with a modal will cause a form window to pop up on the User's client.
 * <h2>Example</h2>
 * <pre>{@code
 *     public void onSlashCommandInteraction(@NonNull SlashCommandInteractionEvent event)
 *     {
 *         if (event.getName().equals("support"))
 *         {
 *             TextInput email = TextInput.create("email", "Email", TextInputStyle.SHORT)
 *                     .setPlaceholder("Enter your E-mail")
 *                     .setRequired(true)
 *                     .setMinLength(10)
 *                     .setMaxLength(100)
 *                     .build();
 *
 *             TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
 *                     .setPlaceholder("Your concerns go here")
 *                     .setRequired(true)
 *                     .setMinLength(30)
 *                     .setMaxLength(1000)
 *                     .build();
 *             Modal modal = Modal.create("support", "Support")
 *                     .addActionRows(ActionRow.of(email), ActionRow.of(body))
 *                     .build();
 *
 *             event.replyModal(modal).queue();
 *         }
 *     }
 * }</pre>
 *
 * <p><b>Only a maximum of 5 components can be included in a Modal, and only {@link net.dv8tion.jda.api.interactions.components.text.TextInput TextInputs} are allowed.</b>
 *
 * @see ModalInteractionEvent
 */
public interface Modal extends SerializableData
{
    /**
     * The custom id of this modal
     *
     * @return The custom id of this modal
     */
    @Nonnull
    String getId();

    /**
     * The title of this modal
     *
     * @return The title of this modal
     */
    @Nonnull
    String getTitle();

    /**
     * A List of {@link net.dv8tion.jda.api.interactions.components.ActionRow ActionRows} that this modal contains.
     *
     * @return List of ActionRows
     */
    @Nonnull
    List<ActionRow> getActionRows();

    /**
     * Creates a new preconfigured {@link Modal.Builder} with the same settings used for this modal.
     * <br>This can be useful to create an updated version of this modal without needing to rebuild it from scratch.
     *
     * @return The {@link Modal.Builder} used to create the modal
     */
    @Nonnull
    default Modal.Builder createCopy()
    {
        return new Builder(getId())
                .setTitle(getTitle())
                .addActionRows(getActionRows());
    }

    /**
     * Creates a new Modal.
     *
     * @param  customId 
     *         The custom id for this modal
     *
     * @param title
     *        The title for this modal
     *
     * @throws IllegalArgumentException
     *         If the provided customId or title are null, empty or blank.
     *
     * @return {@link Builder Builder} instance to customize this modal further
     */
    @Nonnull
    @CheckReturnValue
    static Modal.Builder create(@Nonnull String customId, @Nonnull String title)
    {
        Checks.notNull(customId, "Custom ID");
        Checks.notNull(title, "Title");
        return new Modal.Builder(customId).setTitle(title);
    }

    class Builder
    {
        private String id;
        private String title;
        private final List<ActionRow> components = new ArrayList<>();

        protected Builder(@Nonnull String customId)
        {
            setId(customId);
        }

        /**
         * Sets the custom id for this modal.
         *
         * @param  customId
         *         Custom id
         *
         * @throws IllegalArgumentException
         *         If the provided id is null or blank
         *
         * @return The same builder instance for chaining
         */
        @Nonnull
        public Builder setId(@Nonnull String customId)
        {
            Checks.notBlank(customId, "ID");
            this.id = customId;
            return this;
        }

        /**
         * Sets the title for this modal.
         *
         * @param  title 
         *         The title
         *
         * @throws IllegalArgumentException
         *         If the provided title is null or blank
         *
         * @return The same builder instance for chaining
         */
        @Nonnull
        public Builder setTitle(@Nonnull String title)
        {
            Checks.notBlank(title, "Title");
            this.title = title;
            return this;
        }

        /**
         * Adds ActionRows to this modal
         *
         * @param  actionRows 
         *         ActionRows to add to the modal, up to 5
         *
         * @throws IllegalArgumentException
         *         If any of the provided ActionRows are null
         *
         * @return The same builder instance for chaining
         */
        @Nonnull
        public Builder addActionRows(@Nonnull ActionRow... actionRows)
        {
            Checks.noneNull(actionRows, "Action Rows");
            Collections.addAll(this.components, actionRows);
            return this;
        }

        /**
         * Adds components to this modal
         *
         * @param  actionRows 
         *         ActionRows to add to the modal, up to 5
         *
         * @throws IllegalArgumentException
         *         If any of the provided ActionRows are null
         *
         * @return The same builder instance for chaining
         */
        @Nonnull
        public Builder addActionRows(@Nonnull Collection<? extends ActionRow> actionRows)
        {
            Checks.noneNull(actionRows, "Components");
            this.components.addAll(actionRows);
            return this;
        }

        /**
         * Adds an ActionRow to this modal
         *
         * @param components The components to add
         *
         * @return Same builder for chaining convenience
         */
        @Nonnull
        public Builder addActionRow(@Nonnull Collection<? extends ItemComponent> components)
        {
            return addActionRows(ActionRow.of(components));
        }

        /**
         * Adds an ActionRow to this modal
         *
         * @param components The components to add
         *
         * @return Same builder for chaining convenience
         */
        @Nonnull
        public Builder addActionRow(@Nonnull ItemComponent... components)
        {
            return addActionRows(ActionRow.of(components));
        }

        /**
         * Returns a list of all components
         *
         * @return A list of components
         */
        @Nonnull
        public List<ActionRow> getActionRows()
        {
            return components;
        }

        /**
         * Returns the title
         *
         * @return the title
         */
        @Nonnull
        public String getTitle()
        {
            return title;
        }

        /**
         * Returns the custom id
         *
         * @return the id
         */
        @Nonnull
        public String getId()
        {
            return id;
        }

        /**
         * Builds and returns the {@link Modal}
         *
         * @throws IllegalArgumentException
         * <ul>
         *     <li>If the id is null</li>
         *     <li>If the title is null</li>
         *     <li>If the components are empty</li>
         *     <li>If there are more than 5 components</li>
         * </ul>
         *
         * @return A Modal
         */
        @Nonnull
        public Modal build()
        {
            Checks.check(!components.isEmpty(), "Cannot make a modal with no components!");
            Checks.check(components.size() <= 5, "Cannot make a modal with more than 5 components!");

            return new ModalImpl(id, title, components);
        }
    }
}
