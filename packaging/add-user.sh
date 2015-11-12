#!/bin/bash
# add-user.sh
#
# adds the unprivileged howtomicroservice user - the owner of the how-to-microservice daemon

USERNAME='howtomicroservice'
GROUPNAME='howtomicroservice'
HOW_TO_MICROSERVICE_UID=444
HOW_TO_MICROSERVICE_GID=445
HOMEDIR='/opt/how-to-microservice'
COMMENT='how-to-microservice system user'

getent group $GROUPNAME > /dev/null || groupadd -f -g $HOW_TO_MICROSERVICE_GID -r $GROUPNAME
if ! getent passwd $USERNAME > /dev/null ; then
    if ! getent passwd $HOW_TO_MICROSERVICE_UID > /dev/null ; then
        useradd -r -u $HOW_TO_MICROSERVICE_UID -g $GROUPNAME -d $HOMEDIR -s /sbin/nologin -c "$COMMENT" $USERNAME
    else
        useradd -r -g $GROUPNAME -d $HOMEDIR -s /sbin/nologin -c "$COMMENT" $USERNAME
    fi
fi
exit 0