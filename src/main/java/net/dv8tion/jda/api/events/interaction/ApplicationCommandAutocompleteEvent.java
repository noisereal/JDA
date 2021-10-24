package net.dv8tion.jda.api.events.interaction;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.interactions.CommandInteractionImpl;
import net.dv8tion.jda.internal.requests.RestActionImpl;
import net.dv8tion.jda.internal.requests.Route;
import net.dv8tion.jda.internal.utils.Checks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class ApplicationCommandAutocompleteEvent extends GenericInteractionCreateEvent implements CommandInteraction
{

    private final CommandInteractionImpl interaction;

    public ApplicationCommandAutocompleteEvent(@Nonnull JDA api, long responseNumber, @Nonnull CommandInteractionImpl interaction)
    {
        super(api, responseNumber, interaction);
        this.interaction = interaction;
    }


    @Nonnull
    public RestAction<Void> deferChoices(Collection<Command.Choice> choices)
    {
        DataArray choicesArray = DataArray.empty();
        choices.forEach(choice -> choicesArray.add(DataObject.empty().put("name", choice.getName()).put("value", choice.getAsString())));
        DataObject data = DataObject.empty().put("choices", choicesArray);
        DataObject requestBody = DataObject.empty()
                .put("type", 8)
                .put("data", data);
        return new
                RestActionImpl<>(api, Route.Interactions.CALLBACK.compile(interaction.getId(), interaction.getToken()), requestBody, ((response, voidRequest) -> null));
    }

    @Nonnull
    @Override
    public MessageChannel getChannel()
    {
        return interaction.getChannel();
    }

    @Nonnull
    @Override
    public String getName()
    {
        return interaction.getName();
    }

    @Nullable
    @Override
    public String getSubcommandName()
    {
        return interaction.getSubcommandName();
    }

    @Nullable
    @Override
    public String getSubcommandGroup()
    {
        return interaction.getSubcommandGroup();
    }

    @Override
    public long getCommandIdLong()
    {
        return interaction.getCommandIdLong();
    }

    @Nonnull
    @Override
    public List<OptionMapping> getOptions()
    {
        return interaction.getOptions();
    }

    @Nonnull
    @Override
    public String getCommandString()
    {
        return interaction.getCommandString();
    }
}
